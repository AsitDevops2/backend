package com.raley.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.raley.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {

	//finds user by email id in db
    User findByEmail(String email);
}
