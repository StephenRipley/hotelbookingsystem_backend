package com.fdmgroup.hotelbookingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HotelCityNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(HotelCityNotFoundException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	String hoteCityNotFoundHandler(HotelCityNotFoundException ex) {
		return ex.getMessage();
	}
}
