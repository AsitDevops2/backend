package com.raley.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raley.model.ApiResponse;
import com.raley.model.ResetPasswordDto;
import com.raley.model.User;
import com.raley.model.UserDto;
import com.raley.service.ResetPasswordService;
import com.raley.service.UserService;

/**
 * @author abhay.thakur
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/vendor")
public class VendorController {
	Logger logger = LoggerFactory.getLogger(VendorController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private ResetPasswordService resetPasswordService;
	public VendorController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/register")
	public ApiResponse<User> saveVendor(@RequestBody UserDto user) {
		if (userService.findOne(user.getEmail()) != null) {
			logger.info("Vendor already exist.!: " + user.getEmail());
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Vendor already exist.!", null);
		} else {
			logger.info("Vendor saved successfully " + user.getEmail());
			return new ApiResponse<>(HttpStatus.OK.value(), "Vendor saved successfully.", userService.save(user));
		}
	}
	
	@PostMapping("/resetPassword")
	public ApiResponse<Object> reset(@RequestBody ResetPasswordDto resetPassword) {
		logger.info("find the email : "+resetPassword.getEmail()+ " for reset the password");
		if (userService.findOne(resetPassword.getEmail()) == null) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Email not exist.!", null);
		} else {
			return new ApiResponse<>(HttpStatus.OK.value(), "Password  reset successfully.",
					resetPasswordService.reset(resetPassword.getEmail()));
		}
	}
}
