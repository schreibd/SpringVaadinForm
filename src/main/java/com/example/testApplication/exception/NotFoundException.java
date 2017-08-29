package com.example.testApplication.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private NotFoundExceptionType type;

	public NotFoundException(NotFoundExceptionType notFoundExceptionType) {
		super(notFoundExceptionType.getMessage());
		this.type= notFoundExceptionType;
	}


	public String getErrorMessage() {
		return type.getMessage();
	}

	HttpStatus getStatus() {
		return HttpStatus.NOT_FOUND;
	}
	
	
	public enum NotFoundExceptionType{
		PARTNERNOTFOUNDEXCEPTION("Customer"),
		ADDRESSNOTFOUNDEXCEPTION("Address");
		
		private String type;
		private NotFoundExceptionType(String type){
			this.type=type;
		}
		
		protected String getMessage(){
			return type.concat(" could not be found");
		}
	}
}
