package com.example.demo_jdbc_callbacks;

import com.example.demo_jdbc_callbacks.service.EmployeeReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoJdbcCallbacksApplication implements CommandLineRunner {

	@Autowired
	private EmployeeReportService employeeReportService;

	public static void main(String[] args) {
		SpringApplication.run(DemoJdbcCallbacksApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		employeeReportService.printReport();
	}
}
