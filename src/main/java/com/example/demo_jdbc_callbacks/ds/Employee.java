package com.example.demo_jdbc_callbacks.ds;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@AllArgsConstructor
@Getter
@ToString
public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date hireDate;
    private float salary;

//    public Employee(int id, String firstName, String lastName, String email, String phoneNumber, Date hireDate, float salary) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.hireDate = hireDate;
//        this.salary = salary;
//    }
//
//    @Override
//    public String toString() {
//        return "Employee{" +
//                "id=" + id +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", email='" + email + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", hireDate=" + hireDate +
//                ", salary=" + salary +
//                '}';
//    }
}
