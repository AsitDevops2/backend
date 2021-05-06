package com.raley.service;

import com.raley.model.User;
import com.raley.model.UserDto;

public interface UserService {

    User save(UserDto user);

    User findOne(String email);
}
