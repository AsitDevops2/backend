package com.raley.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.raley.dao.UserDao;
import com.raley.model.User;
import com.raley.service.ResetPasswordService;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {
    
	//Creating logger obbject for logging(uses slf4j logger)
	Logger logger = LoggerFactory.getLogger(ResetPasswordServiceImpl.class);

	//injecting UserDao bean to interact with db
	@Autowired
	private UserDao userDao;
	
	//to encrypt the password
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	/*
	 * findEmail() finds the email in db
	 * returns true if found else false
	 */
	@Override
	public boolean findEmail(String email) {
		logger.info("Finding email : {}",email);
		if (userDao.findByEmail(email) != null) {
			logger.info("{} found!",email);
			return true;
		}
			
		logger.info("{} not found",email);
		return false;
	}

	/*
	 * reset() takes email as parameter to reset the password
	 * calls generatePassword() to generate password
	 * saves generated password into db by email id
	 */
	@Override
	public User reset(String email) {
		User loginObj = userDao.findByEmail(email);	//finds user by email id in db
		String password =generatePassword();		//call to generatePassword()
		logger.info("Resetted password for {} is : {}",email,password);
		
		loginObj.setPassword(bcryptEncoder.encode(password));	//encoding the password
		
		logger.info("Password saved into db successfully");
		return userDao.save(loginObj);
	}

	/*
	 * generatePassword() generates a random password
	 * Password contains small & capital alphabets,digits and special characters
	 * password length is 8 characters
	 */
	@Override
	public String generatePassword() {
		String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		String specialCharacters = "!@#$";
		String numbers = "1234567890";
		
		//combination of letters,digits and special characters
		String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;

		char[] password = new char[8];

		try {
			
			Random random = SecureRandom.getInstanceStrong();
			
			password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
			password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
			password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
			password[3] = numbers.charAt(random.nextInt(numbers.length()));
		
			for (int i = 4; i < 8; i++)
				password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		}
		catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return String.valueOf(password);
	}

}