package com.raley.service.impl;

import com.raley.dao.UserDao;
import com.raley.model.User;
import com.raley.model.UserDto;
import com.raley.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	//Creating logger obbject for logging(uses slf4j logger)
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
		
	//injecting UserDao bean for db interaction
	@Autowired
	private UserDao userDao;

	//to encode the password
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	/*
	 * returns the user by email id
	 */
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.info("finding the user by email {}",email);
		User user = userDao.findByEmail(email);
		if(user == null){
			logger.info("Invalid username or password.");
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		logger.info("User found by email {}",email);
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	/*
	 * returns the user by email id
	 */
	@Override
	public User findOne(String email) {
		logger.info("finding the user by email {}",email);
		User user = userDao.findByEmail(email);
		if(user == null)
			logger.info("{} user not found",email);
		else
			logger.info("{} found!",email);
		
		return user;
	}
	
	/*
	 * save user into db
	 */
	 @Override
	    public User save(UserDto user) {
		 logger.info("saving {} user into db",user.getEmail());
		    User newUser = new User();
		    newUser.setEmail(user.getEmail());
		    newUser.setFirstName(user.getFirstName());
		    newUser.setLastName(user.getLastName());
		    newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		    newUser.setMobile(user.getMobile());
		    newUser.setAddr1(user.getAddr1());
		    newUser.setAddr2(user.getAddr2());
		    newUser.setState(user.getState());
		    newUser.setCountry(user.getCountry());
		    newUser.setPin(user.getPin());
		    newUser.setDept(user.getDept());
		    newUser.setCity(user.getCity());
		    if(user.getRole()!=null)
		    	newUser.setRole(user.getRole());
		    if(user.getParent()!=0)
		    	newUser.setParent(user.getParent());
		    
		    logger.info("User {} saved successfully!",user.getEmail());
	        return userDao.save(newUser);
	    }
}
