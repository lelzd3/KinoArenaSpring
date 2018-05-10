package com.kinoarena.utilities;

import com.kinoarena.utilities.exceptions.InvalidDataException;

public class Validations {

	private Validations() {}

	private static boolean validation(String text)  throws InvalidDataException {
		if( text != null && !text.trim().isEmpty()){
			return true;
		}
		else {
			throw new InvalidDataException("Empty field");
		}
	}
	
	public static boolean verifyEmail(String email) throws InvalidDataException {
	    if(!validation(email)) {
	    	 throw new InvalidDataException("Invalid e-mail, its empty");
	    }
	    
	    if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
	    	 throw new InvalidDataException("Invalid e-mail");
	    }   
	    return true;
	}
	
	public static boolean verifyName(String name) throws InvalidDataException {
		if(!validation(name)) {
			throw new InvalidDataException("Invalid Name, its empty");
	    }

	    if(!name.matches("[a-zA-Z]*")) {
			throw new InvalidDataException("Invalid Name, name should consist only of letters");
	    }
	    
	    if(name.length() > 45) {
	    	throw new InvalidDataException("Invalid Name, name maximum length is 45 characters");
	    }
	        
	    return true;
	}

	public static boolean verifyUsername(String username) throws InvalidDataException {

	    if(!username.matches("[A-Za-z0-9_]+") || username.length() < 5 || username.length() > 45) {
			throw new InvalidDataException("Invalid username, username must 5 - 45 characters long");
	    }
	        
	    return true;
	}
	
	public static boolean verifyPassword(String password) throws InvalidDataException {
		if(!validation(password)) {
			throw new InvalidDataException("Invalid password, its empty");
	    }
		
		if(password.length() > 255) {
			throw new InvalidDataException("Invalid password, password maximum length is 255 characters");
		}
		//		^                 # start-of-string
		//		(?=.*[0-9])       # a digit must occur at least once
		//		(?=.*[a-z])       # a lower case letter must occur at least once
		//		(?=.*[A-Z])       # an upper case letter must occur at least once
		//		(?=.*[@#$%^&+=])  # a special character must occur at least once
		//		(?=\S+$)          # no whitespace allowed in the entire string
		//		.{8,}             # anything, at least eight places though
		//		$                 # end-of-string
							// this constant is useless cuz regex checks for atleast 8 
	    if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) { 
	    	throw new InvalidDataException("Invalid password,Please enter valid password: atleast one digit, one lowercase, one uppercase, at least one symbol! and no whitespace! ");
	    }
	      
	    return true;
	}
	
}
