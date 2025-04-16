package com.example.demo_jdbc_callbacks.dao;

import com.example.demo_jdbc_callbacks.ds.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Employee> findEmployee(){
        return jdbcTemplate.query("select * from employee",
            new RowMapper<Employee>() {
                @Override
                public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new Employee(
                            rs.getInt("employee_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getDate("hire_date"),
                            rs.getFloat("salary")
                    );
                }
            }
        );
    }

    public float findAverageSalaryRowByRow(){
        AverageSalaryRowCallBackHandler averageSalaryRowCallBackHandler=
                new AverageSalaryRowCallBackHandler();

        jdbcTemplate.query(
                "select salary from employee",
                averageSalaryRowCallBackHandler
        );
        return averageSalaryRowCallBackHandler.getAverageSalary();
    }


    private static class AverageSalaryRowCallBackHandler implements RowCallbackHandler{

        private float salarySum = 0;
        private int salaryCount = 0;
        @Override
        public void processRow(ResultSet rs) throws SQLException {
            salarySum += rs.getFloat("salary");
            ++salaryCount;
        }

        public float getAverageSalary(){
            return salarySum / (float) salaryCount;
        }
    }

    public float findAverageSalaryCalculatedOnEntireResultSet(){
        return jdbcTemplate.query(
                "select salary from employee",
                new AverageSalaryResultSetExtractor()
        );
    }


    private static class AverageSalaryResultSetExtractor implements ResultSetExtractor<Float>{

        @Override
        public Float extractData(ResultSet rs) throws SQLException, DataAccessException {
            float salarySum = 0;
            int salaryCount = 0;

            while(rs.next()){
                salarySum += rs.getFloat("salary");
                ++salaryCount;
            }

            return salarySum / (float) salaryCount;
        }
    }

    public double findAverageSalarySqlLevel(){
        return jdbcTemplate.queryForObject(
                "select avg(salary) from employee",
                Double.class
        );
    }

    public double findAverageSalaryModernImplementation(){
        return jdbcTemplate.queryForList(
                "select salary from employee",
                Double.class
        ).stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0f);
    }

    public int findEmployeeIdFromEmail(String email){
        return jdbcTemplate.query(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        return con.prepareStatement("select employee_id from employee where email = ?");
                    }
                },
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, email);
                    }
                },
                new ResultSetExtractor<Integer>() {
                    @Override
                    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                        if(rs.next()){
                            return rs.getInt("employee_id");
                        }else{
                            throw new SQLException("Unable to find id based on email");
                        }
                    }
                }
        );
    }
}
