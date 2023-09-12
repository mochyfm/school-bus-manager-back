package com.transportessalesianoslc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transportessalesianoslc.models.Client;
import com.transportessalesianoslc.models.Message;
import com.transportessalesianoslc.models.Student;
import com.transportessalesianoslc.services.JDBCServicesImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/tslc")
public class ClientsController {
	
	@Autowired
	JDBCServicesImpl genServices;
	
	@GetMapping("/clients")
	public ResponseEntity<List<Client>> getAllClients() {
		try {
			
			List<Client> clients = new ArrayList<Client>();
			genServices.getAllClients().forEach(clients::add);
			return clients.isEmpty() 
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(clients, HttpStatus.OK);
			
		} catch (Exception excep) {
			System.out.println(excep);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/students")
	public ResponseEntity<List<Student>> getAllStudents() {
		try {
			List<Student> students = new ArrayList<Student>();
			genServices.getAllStudents().forEach(students::add);
			return students.isEmpty() 
					? new ResponseEntity<>(HttpStatus.NO_CONTENT)
					: new ResponseEntity<>(students, HttpStatus.OK);
		} catch (Exception excep) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/client")
	public ResponseEntity<Client> getClient(@RequestParam Long id ) {
		try {
			Client client = genServices.getClient(id);
			try {
				return client == null
						? new ResponseEntity<>(HttpStatus.NO_CONTENT)
						: new ResponseEntity<>(client, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception excep) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/student")
	public ResponseEntity<Student> getStudent(@RequestParam Long id ) {
		try {
			
			Student student = genServices.getStudent(id);
			return student == null
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(student, HttpStatus.OK);
			
		} catch (Exception excep) {
			System.out.println(excep);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/msg")
	public ResponseEntity<List<Message>> getMessages(@RequestParam Long id) {
		try {
			List<Message> messages = genServices.getAllMessagesFromStudent(id);
			return messages.isEmpty()
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(messages, HttpStatus.OK);
		} catch (Exception excep) {
			System.out.println(excep);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/client")
	public ResponseEntity<String> newClient(@RequestBody Client client) {
		try {
			return genServices.newClient(new Client(client.getClient_name())) 
					? new ResponseEntity<>("Client was created successfully", HttpStatus.CREATED)
					: new ResponseEntity<>("Could not create client", HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>("An Error has ocurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/student")
	public ResponseEntity<String> newStudent(@RequestBody Student student) {
		try {
			return genServices.newStudent(new Student(student.getStudent_name(), student.getClient_id())) 
					? new ResponseEntity<>("Student was created successfully", HttpStatus.CREATED)
					: new ResponseEntity<>("Could not create student", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("An Error has ocurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/msg")
	public ResponseEntity<String> newMessage(@RequestBody Message message) {
		try {
			return genServices.newMessage(new Message(message.getMessage(), message.getMessage_type(), 
					message.getStudent_id(), message.getClient_id()))
					? new ResponseEntity<>("Message was sent successfully", HttpStatus.CREATED)
					: new ResponseEntity<>("Could not create student", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("An Error has ocurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/client")
	public ResponseEntity<String> updateClient(@RequestParam Long id, String newName) {
		try {
			return genServices.updateClient(id, newName)
					? new ResponseEntity<>("Client was updated successfully", HttpStatus.CREATED)
					: new ResponseEntity<>("Could not delete student", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("An Error has ocurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/student")
	public ResponseEntity<String> deleteStudent(@RequestParam Long id ) {
		try {
			return genServices.removeStudent(id)
					? new ResponseEntity<>("Student was deleted successfully", HttpStatus.CREATED)
					: new ResponseEntity<>("Could not delete student", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("An Error has ocurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/client")
	public ResponseEntity<String> deleteClient(@RequestParam Long id ) {
		try {
			return genServices.removeClient(id)
					? new ResponseEntity<>("Client was deleted successfully", HttpStatus.CREATED)
					: new ResponseEntity<>("Could not delete client", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("An Error has ocurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
