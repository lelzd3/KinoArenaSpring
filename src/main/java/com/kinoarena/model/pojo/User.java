package com.kinoarena.model.pojo;

import com.kinoarena.utilities.Validations;
import com.kinoarena.utilities.exceptions.InvalidDataException;

public class User {
	
	protected int id;
	protected String firstname; 
	protected String lastname; 
	protected String username;
	protected String password;
	protected String email;
	private Cinema cinema;
	protected boolean isAdmin;
	protected int age;
	
	

	public User(int age ,String username,String password,String firstname,String lastname,String email) throws InvalidDataException {
		setAge(age);
		setUsername(username);
		setPassword(password);
		setFirstName(firstname);
		setLastName(lastname);
		setEmail(email);
		isAdmin=false;
	}
	
	public User(int id,int age, String username,String password, String firstname,String lastname, String email,boolean isAdmin) throws InvalidDataException {
		this(age , username, password, firstname, lastname, email);
		setId(id);
		this.isAdmin= isAdmin;
	}
	
	
	
	// getters:
	public int getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}
	
	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
		
	public Cinema getCinema() {
		return cinema;
	}

	
	public String getEmail() {
		return email;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}
	
	public int getAge() {
		return age;
	}

	// setters:
	public void setId(int id) {
		this.id = id;
	}
	
	public void setUsername(String username) throws InvalidDataException {
		if(Validations.verifyUsername(username)){
			this.username = username;
		}

	}

	public void setPassword(String password) throws InvalidDataException {
		if(Validations.verifyPassword(password)){
			this.password = password;
		}
	}
	
	public void setFirstName(String name) throws InvalidDataException {
		if(Validations.verifyName(name)) {
			this.firstname = name;
		}
	}

	public void setLastName(String name) throws InvalidDataException {
		if(Validations.verifyName(name)) {
			this.lastname = name;
		}
	}
	
	public void setEmail(String email) throws InvalidDataException{
		if(Validations.verifyEmail(email)){
			this.email = email;
		}
	}

	public void setAge(int age) throws InvalidDataException {
		if(age>=0 && age<=120) {
		this.age = age;
		}
		else {
			throw new InvalidDataException("Invalid age entered! Age must be between 0 and 120");
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", isAdmin=" + isAdmin + "]";
	}

	



	
}
