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

import com.raley.model.Response;
import com.raley.model.ResetPasswordDto;
import com.raley.model.User;
import com.raley.model.UserDto;
import com.raley.service.ResetPasswordService;
import com.raley.service.UserService;

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
@Api(value="VendorController")
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

	@ApiOperation(value="saveVendor" ,notes ="This method is to save vendor details")
	@ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 400, message = "Bad Request!")})
	@PostMapping("/register")
	public Response<User> saveVendor(@RequestBody UserDto user) {
		if (userService.findOne(user.getEmail()) != null) {
			logger.info("Vendor already exist.!: " + user.getEmail());
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Vendor already exist.!", null);
		} else {
			logger.info("Vendor saved successfully " + user.getEmail());
			return new Response<>(HttpStatus.OK.value(), "Vendor saved successfully.", userService.save(user));
		}
	}
	
	@ApiOperation(value="reset" ,notes ="This method is reset password")
	@ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 400, message = "Bad Request!")})
	@PostMapping("/resetPassword")
	public Response<Object> reset(@RequestBody ResetPasswordDto resetPassword) {
		logger.info("find the email : "+resetPassword.getEmail()+ " for reset the password");
		if (userService.findOne(resetPassword.getEmail()) == null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Email not exist.!", null);
		} else {
			return new Response<>(HttpStatus.OK.value(), "Password  reset successfully.",
					resetPasswordService.reset(resetPassword.getEmail()));
		}
	}
}
