package com.transportessalesianoslc.models;

import java.util.List;

public class Client {
	
	private Long client_id;
	private String client_name;
	private List<Student> students;
	
	public Client() {
		
	}
	
	public Client(String client_name) {
		this.client_name = client_name;
	}
	
	public Client(Long client_id, String client_name, List<Student> students) {
		this.client_id = client_id;
		this.client_name = client_name;
		this.students = students;
	}
	
	public Long getClient_id() {
		return client_id;
	}
	
	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}
	
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	
	
}
