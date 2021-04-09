package com.raley.service;

import com.raley.model.User;

public interface ResetPasswordService 
{
	public boolean findEmail(String email);
	public String generatePassword();
	public User reset(String email);
}