package com.kinoarena.utilities;

import org.springframework.stereotype.Component;
import com.kinoarena.utilities.exceptions.InvalidDataException;

@Component
public class Validations {

	private static final int MIN_LENGTH_OF_PASSWORD = 8;

	private Validations() {}
	
	public static boolean inputValidation(String username, String password) throws InvalidDataException {
		if(validation(username) && validation(password)) {
			return true;
		}
		else {
			throw new InvalidDataException("Invalid User name or password");
		}
	}

	public static boolean validation(String text)  throws InvalidDataException {
		if( text != null && !text.trim().isEmpty()){
			return true;
		}
		else {
			throw new InvalidDataException("Invalid User name or password");
		}
	}
	
	public static boolean verifyEmail(String email) throws InvalidDataException {
	    if(!validation(email)) {
	    	 throw new InvalidDataException("Invalid e-mail");
	    }
	    
	    if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
	    	 throw new InvalidDataException("Invalid e-mail");
	    }   
	    return true;
	}
	
	public static boolean verifyName(String name) throws InvalidDataException {
		if(!validation(name)) {
			throw new InvalidDataException("Invalid Name");
	    }

	    if(!name.matches("[a-zA-Z]*")) {
			throw new InvalidDataException("Invalid Name");
	    }
	        
	    return true;
	}
	
	public static boolean verifyPhoneNumber(String number) throws InvalidDataException {
		if(!validation(number)) {
			throw new InvalidDataException("Invalid Phone number");
	    }

	    if(number.length() != 10 || !number.matches("[0-9]+")) {
			throw new InvalidDataException("Invalid Phone number");
	    }
	        
	    return true;
	}
	
	public static boolean verifyUsername(String username) throws InvalidDataException {

	    if(!username.matches("[A-Za-z0-9_]+") || username.length() < 6) {
			throw new InvalidDataException("Invalid username");
	    }
	        
	    return true;
	}
	
	public static boolean verifyPassword(String password) throws InvalidDataException {
		if(!validation(password)) {
			throw new InvalidDataException("Invalid password");
	    }
// old regex "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$)"
// new regex "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
//		^                 # start-of-string
//		(?=.*[0-9])       # a digit must occur at least once
//		(?=.*[a-z])       # a lower case letter must occur at least once
//		(?=.*[A-Z])       # an upper case letter must occur at least once
//		(?=.*[@#$%^&+=])  # a special character must occur at least once
//		(?=\S+$)          # no whitespace allowed in the entire string
//		.{8,}             # anything, at least eight places though
//		$                 # end-of-string
							// this constant is useless cuz regex checks for atleast 8 
	    if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$") || password.length() < MIN_LENGTH_OF_PASSWORD) { 
	    	System.out.println("Please enter valid password: atleast one digit, one lowercase, one uppercase, at least one symbol! and no whitespace!");
	    	throw new InvalidDataException("Invalid password");
	    }
	        
	    return true;
	}
	
}
