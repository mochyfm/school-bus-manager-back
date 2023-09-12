package com.transportessalesianoslc.models;

import java.util.List;

public class Student {
	
	private Long student_id;
	private String student_name;
	private Long client_id;
	private List<Message> messages;
	
	public Student() {
		
	}
	
	public Student(String student_name, Long client_id) {
		this.student_name = student_name;
		this.client_id = client_id;
	}
	
	public Student(Long student_id, String student_name, Long client_id) {
		this.student_id = student_id;
		this.student_name = student_name;
	}

	public Long getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public Long getClient_id() {
		return client_id;
	}

	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> list) {
		this.messages = list;
	}

}
