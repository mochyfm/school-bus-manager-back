package com.transportessalesianoslc.services;

import java.util.List;

import com.transportessalesianoslc.models.BusRoute;
import com.transportessalesianoslc.models.BusStop;
import com.transportessalesianoslc.models.Client;
import com.transportessalesianoslc.models.Message;
import com.transportessalesianoslc.models.Student;

public interface JDBCServices {
	
	/* Clients Related Services */
	
	/* HTTP - GET VERB (METHODS) */
	
	public List<Client> getAllClients();
	public List<Student> getAllStudents(); 
	public List<List<BusStop>> getAllAsignedStops();
	public List<BusStop> getUnassignedStops();
	
	public Client getClient(Long client_id);
	public Student getStudent(Long student_id);
	
	public List<Message> getAllMessagesFromStudent(Long student_id);
	
	public List<BusRoute> getAssignedRoutesFromStudent(Long student_id);
	public List<BusRoute> getNonAssignedRoutesFromStudent(Long student_id);
	
	/* HTTP - POST VERB (METHODS) */
	
	public boolean newClient(Client client);
	public boolean newStudent(Student Student);
	public boolean newMessage(Message message);
	public boolean globalMessage(Long route_id, Message message);
	
	/* HTTP - DELETE VERB (METHODS) */
	
	public boolean removeStudent(Long student_id);
	public boolean removeClient(Long student_id);
	
	/* Transport Related Services */
	
	/* HTTP - GET VERB (METHODS) */
	
	public List<BusStop> getAllStops();
	public List<BusRoute> getAllRoutes();
	public BusRoute getRoute(Long route_id);
	public BusStop getStopById(Long stop_id);
	
	/* HTTP - DELETE VERB (METHODS) */
	
	public boolean removeRoute(Long route_id);
	
	/* HTTP - POST VERB (METHODS) */
	
	public boolean newStops(List<BusStop> stops);
	public boolean newRoute(BusRoute route);
	public boolean assignRouteToStudent(Long route_id, Long stop_id, Long student_id);
	
	/* HTTP - PUT VERB (METHODS) */
	
	public boolean updateClient(Long client_id, String client_name);
	public boolean assignStopsToRoute(Long route_id, List<BusStop> stops);


}
