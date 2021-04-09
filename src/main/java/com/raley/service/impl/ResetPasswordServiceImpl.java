package com.raley.service.impl;

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
    Logger logger = LoggerFactory.getLogger(ResetPasswordServiceImpl.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Override
	public boolean findEmail(String email) {
		if (userDao.findByEmail(email) != null)
			return true;

		return false;
	}

	@Override
	public User reset(String email) {
		User loginObj = userDao.findByEmail(email);
		String password =generatePassword();
		logger.info("Reset password is : "+password);
		loginObj.setPassword(bcryptEncoder.encode(password));
		return userDao.save(loginObj);
	}

	@Override
	public String generatePassword() {
		String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		String specialCharacters = "!@#$";
		String numbers = "1234567890";
		String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;

		Random random = new Random();
		char[] password = new char[8];

		password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
		password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
		password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
		password[3] = numbers.charAt(random.nextInt(numbers.length()));

		for (int i = 4; i < 8; i++) {
			password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		}

		return password.toString();
	}

}