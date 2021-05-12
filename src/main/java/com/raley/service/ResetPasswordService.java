package com.raley.service;

import com.raley.model.User;

public interface ResetPasswordService 
{
	//finds email id in db and returns true/false
	public boolean findEmail(String email);
	
	//generates random password
	public String generatePassword();
	
	//save generated password in db
	public User reset(String email);
	
	//sends mail to requested user
	public void sendEmail(String recipient,String password);
}