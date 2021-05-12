package com.raley.model;

public class Email {
	
	private String recipient;
	private String subject;
	private String content;
	
	public Email() {
		super();
	}
	public Email(String recipient, String subject, String content) {
		super();
		this.recipient = recipient;
		this.subject = subject;
		this.content = content;
	}
	
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
