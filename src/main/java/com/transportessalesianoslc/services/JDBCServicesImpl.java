package com.transportessalesianoslc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transportessalesianoslc.models.BusRoute;
import com.transportessalesianoslc.models.BusStop;
import com.transportessalesianoslc.models.Client;
import com.transportessalesianoslc.models.Message;
import com.transportessalesianoslc.models.Student;
import com.transportessalesianoslc.repository.ClientsRepositoryImpl;
import com.transportessalesianoslc.repository.TransportRepositoryImpl;

@Service
public class JDBCServicesImpl implements JDBCServices {
	
	@Autowired
	ClientsRepositoryImpl clientRepository;
	
	@Autowired
	TransportRepositoryImpl transportRepository;

	@Override
	public List<Client> getAllClients() {
		return clientRepository.getAllClients();
	}
	
	@Override
	public List<Student> getAllStudents() {
		return clientRepository.getAllStudents();
	}
	
	@Override
	public Student getStudent(Long student_id) {
		return clientRepository.getStudent(student_id);
	}
	
	@Override
	public Client getClient(Long client_id) {
		return clientRepository.getClient(client_id);
	}
	
	@Override
	public List<Message> getAllMessagesFromStudent(Long student_id) {
		return clientRepository.getAllMessagesFromStudent(student_id);
	}

	@Override
	public boolean newClient(Client client) {
		return clientRepository.newClient(client) == 1;
	}
	
	@Override
	public boolean newStudent(Student student) {
		return clientRepository.newStudent(student) == 1;
	}

	@Override
	public boolean newMessage(Message message) {
		return clientRepository.newMessage(message) == 1;
	}

	@Override
	public boolean removeClient(Long client_id) {
		return clientRepository.removeClient(client_id) == 1;
	}

	@Override
	public boolean updateClient(Long client_id, String client_name) {
		return clientRepository.updateClient(client_id, client_name) == 1;
	}
	
	@Override
	public boolean removeStudent(Long student_id) {
		return clientRepository.removeStudent(student_id) == 1;
	}

	@Override
	public List<BusStop> getAllStops() {
		return transportRepository.getAllStops();
	}

	@Override
	public List<BusRoute> getAllRoutes() {
		return transportRepository.getAllRoutes();
	}

	public List<List<BusStop>> getAllAsignedStops() {
		return transportRepository.getAllAsignedStops();
	}
	
	@Override
	public BusRoute getRoute(Long route_id) {
		return transportRepository.getRoute(route_id);
	}

	@Override
	public BusStop getStopById(Long stop_id) {
		return transportRepository.getStop(stop_id);
	}

	@Override
	public boolean removeRoute(Long route_id) {
		return transportRepository.removeBusRoute(route_id) == 1;
	}

	@Override
	public boolean newStops(List<BusStop> stop) {
		return transportRepository.setStops(stop) == 1;
	}

	@Override
	public boolean newRoute(BusRoute route) {
		return transportRepository.newBusRoute(route) == 1;
	}

	@Override
	public boolean assignStopsToRoute(Long route_id, List<BusStop> stops) {
		return transportRepository.assignStopsToRoute(route_id, stops) == 1;
	}

	@Override
	public List<BusStop> getUnassignedStops() {
		return transportRepository.getUnassignedStops();
	}

	@Override
	public List<BusRoute> getAssignedRoutesFromStudent(Long student_id) {
		return transportRepository.getAssignedRoutesFromStudent(student_id);
	}

	@Override
	public List<BusRoute> getNonAssignedRoutesFromStudent(Long student_id) {
		return transportRepository.getNonAssignedRoutesFromStudent(student_id);
	}

	@Override
	public boolean assignRouteToStudent(Long route_id, Long stop_id, Long student_id) {
		return transportRepository.assignStopToStudent(route_id, stop_id, student_id) == 1;
	}

	@Override
	public boolean globalMessage(Long route_id, Message message) {
		return clientRepository.sendMessageFromRoute(route_id, message) == 1;
	}

	

}
