package com.dev.employee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepo employeeRepo;
	
	@PostMapping("/")
	public Employee saveEmployee(@RequestBody @Validated Employee emp, BindingResult  errors) {
		System.out.println(emp);
		if(errors.hasErrors()) {
			System.out.println("error in employee create request");
			throw new IllegalArgumentException("Employee input request does not contain valid input");
		}
		emp = employeeRepo.save(emp);
		return emp;
	}
	
	
	@GetMapping("/")	
	public List<Employee> getEmployees(@RequestParam(name="page",defaultValue="0")int page, @RequestParam(name="size",defaultValue="10")int size) {		
		List<Employee> emps = new ArrayList<>();
		if(size==0) size=10;
		Pageable p = PageRequest.of(page, size);
		this.employeeRepo.findAll(p).forEach(emps::add);
		return emps;	
	}
	
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public void handleIllegalArgException(IllegalArgumentException e) {		
	}

	@ExceptionHandler(value = HttpMessageConversionException.class)
	public ResponseEntity<String> handleIllegalArgException(HttpMessageConversionException e) {
		ResponseEntity<String> resp = new ResponseEntity<>("Error handling request", HttpStatus.INTERNAL_SERVER_ERROR);
		return resp;
	}

	
}
