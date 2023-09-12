package com.transportessalesianoslc.models;

import java.sql.Date;

public class Message {
	
	private Long message_id;
	private String message;
	private String message_type;
	private Long student_id;
	private Long client_id;
	private String sended_at;
	
	public Message() {
	}
	
	public Message(String message, String message_type) {
		this.message = message;
		this.message_type = message_type;
	}

	public Message(String message, String message_type, Long student_id, Long client_id) {
		this.message = message;
		this.message_type = message_type;
		this.student_id = student_id;
		this.client_id = client_id;
	}
	
	public Message(Long message_id, String message, String message_type, Long student_id, Long client_id, String sended_at) {
		this.message_id = message_id;
		this.message = message;
		this.message_type = message_type;
		this.student_id = student_id;
		this.client_id = client_id;
		this.sended_at = sended_at;
	}

	public Long getMessage_id() {
		return message_id;
	}

	public void setMessage_id(Long message_id) {
		this.message_id = message_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public Long getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}

	public Long getClient_id() {
		return client_id;
	}

	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}

	public String getSended_at() {
		return sended_at;
	}

	public void setSended_at(String sended_at) {
		this.sended_at = sended_at;
	}
	
	
	
	

}
