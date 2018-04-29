package com.kinoarena.utilities.exceptions;

@SuppressWarnings("serial")
public class NotAnAdminException extends Exception {
	
	public NotAnAdminException(){
		super("User is not an admin! The action is allowed only for admins!");
	}
	
	public NotAnAdminException(Throwable cause){
		super("User is not an admin! The action is allowed only for admins!", cause);
	}

}
