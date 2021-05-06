package com.raley.service;

import com.raley.model.User;
import com.raley.model.UserDto;

public interface UserService {

	//save user in db
    User save(UserDto user);
    
    //finds user by email id
    User findOne(String email);
}
