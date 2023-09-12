package com.transportessalesianoslc.repository;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.transportessalesianoslc.models.Client;
import com.transportessalesianoslc.models.Message;
import com.transportessalesianoslc.models.Student;
import com.transportessalesianoslc.repository.querys.RepositoryQuerys;

@Repository
public class ClientsRepositoryImpl implements ClientsRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Method created for returning all the clients of the Database
	 * 
	 * @returns List of Clients or null if there is none. 
	 * 
	 */
	@Override
	public List<Client> getAllClients() {
		List<Client> clients = jdbcTemplate.query(
			RepositoryQuerys.SQL_SELECT_ALL_CLIENTS, 
			BeanPropertyRowMapper.newInstance(Client.class));
		return fillWithStudents(clients);
	}
	
	private List<Client> fillWithStudents(List<Client> clients) {
		List<Client> outputClients = clients;	
		for (Iterator<Client> iterator = outputClients.iterator(); iterator.hasNext();) {
			Client innerClient = (Client) iterator.next();
			innerClient.setStudents(fillWithMessages(jdbcTemplate.query(
					RepositoryQuerys.SQL_SELECT_ALL_STUDENTS_FROM_CLIENT, 
					BeanPropertyRowMapper.newInstance(Student.class), innerClient.getClient_id())));
		}
		return outputClients;
	}

	/**
	 * 
	 * Method created for returning all the students of the Database
	 * @returns List of Student or null if there is none. 
	 * 
	 */
	@Override
	public List<Student> getAllStudents() {
		return  fillWithMessages(jdbcTemplate.query(
				RepositoryQuerys.SQL_SELECT_ALL_STUDENTS, 
				BeanPropertyRowMapper.newInstance(Student.class)));
	}
	
	private List<Student> fillWithMessages(List<Student> students) {
		List<Student> outputStudents = students;
		for (Iterator<Student> iterator = outputStudents.iterator(); iterator.hasNext();) {
			Student innerStudent = (Student) iterator.next();
			innerStudent.setMessages(jdbcTemplate.query(
					RepositoryQuerys.SQL_SELECT_MESSAGES_FROM_STUDENT, 
					BeanPropertyRowMapper.newInstance(Message.class), innerStudent.getStudent_id()));
		}
		return outputStudents;
	}
	
	/**
	 * 
	 * Method created for returning a student from the Database by the given id.
	 * @returns The Student or null if there is none. 
	 * 
	 */
	@Override
	public Student getStudent(Long student_id) {
		return fillWithMessages(jdbcTemplate.queryForObject(
				RepositoryQuerys.SQL_SELECT_STUDENT_BY_ID, 
				BeanPropertyRowMapper.newInstance(Student.class), student_id));
	}
	
	private Student fillWithMessages(Student student) {
		Student outputStudent = student;
		outputStudent.setMessages(jdbcTemplate.query(
				RepositoryQuerys.SQL_SELECT_MESSAGES_FROM_STUDENT, 
				BeanPropertyRowMapper.newInstance(Message.class), student.getStudent_id()));
		return outputStudent;
	}
	
	/**
	 * 
	 * Method created for returning a client from the Database by the given id.
	 * @returns The Client or null if there is none. 
	 * 
	 */
	@Override
	public Client getClient(Long client_id) {
		Client client = jdbcTemplate.queryForObject(
						RepositoryQuerys.SQL_SELECT_CLIENT_BY_ID, 
						BeanPropertyRowMapper.newInstance(Client.class), client_id);
		return fillWithStudents(client);
	}
	
	private Client fillWithStudents(Client client) {
		Client outputClient = client;	
		outputClient.setStudents(fillWithMessages(jdbcTemplate.query(
					RepositoryQuerys.SQL_SELECT_ALL_STUDENTS_FROM_CLIENT, 
					BeanPropertyRowMapper.newInstance(Student.class), client.getClient_id())));
		return outputClient;
	}


	@Override
	public List<Message> getAllMessagesFromStudent(Long student_id) {
		return jdbcTemplate.query(RepositoryQuerys.SQL_SELECT_MESSAGES_FROM_STUDENT, 
				BeanPropertyRowMapper.newInstance(Message.class), student_id);
	}

	@Override
	public int newClient(Client client) {
		return jdbcTemplate.update(
				RepositoryQuerys.SQL_INSERT_CLIENT,
				new Object[] { 
						client.getClient_name()
					} 
				);
	}
	
	@Override
	public int newStudent(Student student) {
		return jdbcTemplate.update(
				RepositoryQuerys.SQL_INSERT_STUDENT,
				new Object[] { 
						student.getStudent_name(),
						student.getClient_id(),
					} 
				);
	}

	@Override
	public int newMessage(Message message) {
		return jdbcTemplate.update(
				RepositoryQuerys.SQL_INSERT_MESSAGE,
				new Object[] { 
						message.getMessage(),
						message.getMessage_type(),
						message.getStudent_id(),
						message.getClient_id(),
					} 
				);
	}


	@Override
	public int removeClient(Long client_id) {
		return jdbcTemplate.update(RepositoryQuerys.SQL_DELETE_CLIENT, client_id);
	}
	
	@Override
	public int removeStudent(Long student_id) {
		return jdbcTemplate.update(RepositoryQuerys.SQL_DELETE_STUDENT, student_id);
	}
	
	@Override
	public int updateClient(Long client_id, String client_name) {
		return jdbcTemplate.update(RepositoryQuerys.SQL_UPDATE_CLIENT_NAME, new Object[] {
				client_name, 
				client_id
		});
	}


	@Override
	public int sendMessageFromRoute(Long route_id, Message message) {
		try {
			
			Message localMessage = message;
			List<Student> assignedStudents = jdbcTemplate.query(
					RepositoryQuerys.SQL_SELECT_STUDENTS_FROM_ROUTE, 
					BeanPropertyRowMapper.newInstance(Student.class), route_id);
			
			for (Iterator iterator = assignedStudents.iterator(); iterator.hasNext();) {
				Student student = (Student) iterator.next();
				
				localMessage.setClient_id(student.getClient_id());
				localMessage.setStudent_id(student.getStudent_id());
				
				newMessage(message);
			}
			
			return 1;	
		} catch (Exception e) {
			return 0;		
		}
	}

	
}

