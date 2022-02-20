package com.dev.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	@PostMapping("/")
	public Employee saveEmployee(@RequestBody @Validated Employee emp, BindingResult  errors) {
		System.out.println(emp);
		if(errors.hasErrors()) {
			System.out.println("error in employee create request");
			throw new IllegalArgumentException("Employee input request does not contain valid input");
		}
		return emp;
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
