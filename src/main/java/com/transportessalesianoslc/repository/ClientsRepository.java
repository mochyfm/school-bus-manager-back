package com.transportessalesianoslc.repository;

import java.util.List;

import com.transportessalesianoslc.models.Client;
import com.transportessalesianoslc.models.Message;
import com.transportessalesianoslc.models.Student;

/**
 * 
 * Package realized for the "ClientRepository", located on the Back-End of the
 * Client management section. This interface has all the methods that are required
 * on such section, and they are not related with the TransportRepository.
 * 
 * @see ClientRepository
 * @author Oceánida S.L
 *
 */

public interface ClientsRepository {
	
	/* HTTP - GET VERB (METHODS) */
	
	public List<Client> getAllClients();
	public List<Student> getAllStudents(); 
	
	public Client getClient(Long client_id);
	public Student getStudent(Long student_id);
	
	public List<Message> getAllMessagesFromStudent(Long student_id);
	

	/* HTTP - POST VERB (METHODS) */
	
	public int newClient(Client client);
	public int newStudent(Student Student);
	public int newMessage(Message message);
	public int sendMessageFromRoute(Long route_id, Message message);
	
	/* HTTP - DELETE VERB (METHODS) */
	
	public int removeClient(Long client_id);
	public int removeStudent(Long student_id);
	
	/* HTTP - PUT VERB (METHODS) */
	
	public int updateClient(Long client_id, String client_name);
	
}
