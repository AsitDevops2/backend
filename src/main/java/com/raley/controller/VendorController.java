package com.raley.controller;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raley.model.ResetPasswordDto;
import com.raley.model.Response;
import com.raley.model.User;
import com.raley.model.UserDto;
import com.raley.service.ResetPasswordService;
import com.raley.service.UserService;
import com.raley.utility.EmailUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author abhay.thakur
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/vendor")
@Api(value = "VendorController")
public class VendorController {
	
	//Creating logger object for logging(uses slf4j logger)
	Logger logger = LoggerFactory.getLogger(VendorController.class);
	
	//Injecting userService bean
	@Autowired
	private UserService userService;
	
	//Injecting resetPasswordService bean
	@Autowired
	private ResetPasswordService resetPasswordService;

	public VendorController(UserService userService) {
		super();
		this.userService = userService;
	}

	/*
	 * saveVendor() used to save the vendor
	 * uses userDto model object as parameter
	 */
	@ApiOperation(value = "saveVendor", notes = "This method is to save vendor details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping("/register")
	public Response<User> saveVendor(@RequestBody UserDto user) {
		if (userService.findOne(user.getEmail()) != null) 
		{
			logger.info("Vendor {} already exist.!:", user.getEmail());
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Vendor already exist.!", null);
		} 
		
		return new Response<>(HttpStatus.OK.value(), "Vendor saved successfully.", userService.save(user));
	}

	/*
	 * reset() is used to reset the password
	 * takes email as parameter
	 */
	@ApiOperation(value = "reset", notes = "This method is reset password")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping("/resetPassword")
	public Response<Object> reset(@RequestBody ResetPasswordDto resetPassword) {

		logger.info("find the email : {} for reset the password", resetPassword.getEmail());

		if (userService.findOne(resetPassword.getEmail()) == null) 
		{
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Email not exist.!", null);
		} 
		else 
		{
			logger.info("Password reset successfully for {} this email", resetPassword.getEmail());
			return new Response<>(HttpStatus.OK.value(), "Password  reset successfully.",
					resetPasswordService.reset(resetPassword.getEmail()));
		}
	}

}
