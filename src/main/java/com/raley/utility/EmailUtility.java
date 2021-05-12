package com.raley.utility;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.raley.config.EmailConfig;
import com.raley.model.Email;
import com.raley.model.User;
import com.raley.service.UserService;

@Service
public class EmailUtility {

	@Autowired
	private EmailConfig emailConfig;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public ResponseEntity<String> sendEmail(String recipient,String password) {
		String url=emailConfig.getHost()+"/email/sent";
		User user=userService.findOne(recipient);
		String name=user.getFirstName();
		String content=MessageFormat.format(emailConfig.getContent(), name,password);
		
		Email emailRequest=new Email(recipient, emailConfig.getSubject(),content); 
		
		return restTemplate.postForEntity(url, emailRequest, String.class);
	}
	
}
