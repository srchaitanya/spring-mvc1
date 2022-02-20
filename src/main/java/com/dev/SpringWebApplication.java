package com.dev;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dev.employee.Employee;
import com.dev.employee.EmployeeRepo;

@SpringBootApplication
public class SpringWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebApplication.class, args);
	}
	
	
	@Bean
	public CommandLineRunner initialEmployeeData(final EmployeeRepo employeeRepo) {
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				String firstName = "R";
				String lastName  = "S";
				int age         = 40;
				
				for(int i=0;i<30;i++) {
					Employee e = new Employee(firstName+i, lastName+i, age+i);
					employeeRepo.save(e);
					System.out.println(e);
				}
				
				
			}
		};
		
	}

}
