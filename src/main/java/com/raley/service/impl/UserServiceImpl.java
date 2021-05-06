package com.raley.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.raley.dao.UserDao;
import com.raley.model.User;
import com.raley.model.UserDto;
import com.raley.service.UserService;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findByEmail(email);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public User findOne(String email) {
		return userDao.findByEmail(email);
	}
	
	 @Override
	    public User save(UserDto user) {
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
		    
	        return userDao.save(newUser);
	    }
}
