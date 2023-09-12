package com.transportessalesianoslc.repository.querys;

public class RepositoryQuerys {
	
	/* SELECT QUERYS */
	
	public static final String SQL_SELECT_ALL_CLIENTS = "SELECT * FROM clients";
	public static final String SQL_SELECT_ALL_STUDENTS_FROM_CLIENT = "SELECT student_id, student_name, client_id FROM students WHERE client_id = ?";

	public static final String SQL_SELECT_ALL_STUDENTS = "SELECT * FROM students";
	
	public static final String SQL_SELECT_CLIENT_BY_ID = "SELECT * FROM clients WHERE client_id = ?";
	public static final String SQL_SELECT_STUDENT_BY_ID = "SELECT * FROM students WHERE student_id = ?";
	
	public static final String SQL_SELECT_CLIENT_FROM_STUDENT = "SELECT client_id, client_name FROM clients WHERE client_id = (SELECT client_id FROM students WHERE student_id = ?)";
	public static final String SQL_SELECT_MESSAGES_FROM_STUDENT = "SELECT * FROM messages WHERE student_id = (SELECT student_id FROM students WHERE student_id = ?)";
	
	public static final String SQL_SELECT_ALL_STOPS = "SELECT * FROM stops";
	public static final String SQL_SELECT_STOP_BY_ID = "SELECT * FROM stops WHERE stop_id = ?";
	
	public static final String SQL_SELECT_ALL_ROUTES = "SELECT * FROM routes";
	public static final String SQL_SELECT_ROUTE_BY_ID = "SELECT * FROM routes WHERE route_id = ?";
	public static final String SQL_SELECT_RELATED_STOPS = "SELECT * FROM stops WHERE stop_id = (SELECT stop_id FROM stops_routes WHERE stop_id = stops.stop_id AND route_id = ?)";
	public static final String SQL_SELECT_NON_RELATED_STOPS = "SELECT * FROM stops WHERE stop_id NOT IN (SELECT stop_id FROM stops_routes)";
	
	public static final String SQL_SELECT_ROUTES_FROM_STUDENT = "SELECT * FROM routes WHERE route_id IN (SELECT route_id FROM students_routes WHERE student_id = ?)";
	public static final String SQL_SELECT_STUDENTS_FROM_ROUTE = "SELECT * FROM students WHERE student_id IN (SELECT student_id FROM students_routes WHERE route_id = ?)";
	public static final String SQL_SELECT_RELATED_STOPS_TO_STUDENT_ON_ROUTE = "SELECT * FROM stops WHERE stop_id = (SELECT stop_id FROM students_routes WHERE stop_id = stops.stop_id AND route_id = ? AND student_id = ?)";
	public static final String SQL_SELECT_NON_RELATED_ROUTES_FROM_STUDENT = "SELECT * FROM routes WHERE route_id NOT IN (SELECT route_id FROM students_routes WHERE student_id = ?)";
	
	/* INSERT QUERYS */
	
	public static final String SQL_INSERT_CLIENT = "INSERT INTO clients(client_name) VALUES (?)";
	public static final String SQL_INSERT_STUDENT = "INSERT INTO students(student_name, client_id) VALUES (?, ?)";
	public static final String SQL_INSERT_MESSAGE = "INSERT INTO messages(message, message_type, student_id, client_id) VALUES (?,?,?,?)";
	
	
	public static final String SQL_INSERT_STOP = "INSERT INTO stops(lat, lng) VALUES (?,?)";
	public static final String SQL_INSERT_ROUTE = "INSERT INTO routes(label, route_type) VALUES (?,?)";
	
	public static final String SQL_ASSIGN_STOP = "INSERT INTO stops_routes(route_id, stop_id) VALUES (?, ?)";
	
	public static final String SQL_INSERT_MESSAGE_FROM_ROUTE = "INSERT INTO message(message, message_type, student_id, client_id) VALUES(?, ?, ?, ?)";
	public static final String SQL_ASSIGN_STUDENT_TO_ROUTE = "INSERT INTO students_routes(route_id, stop_id, student_id) VALUES (?, ?, ?)";
	
	/* UPDATE QUERTS */
	
	public static final String SQL_UPDATE_CLIENT_NAME = "UPDATE clients SET client_name = ? WHERE client_id = ?";
	
	
	/* DELETE QUERYS */
	
	public static final String SQL_DELETE_CLIENT  = "DELETE FROM clients WHERE client_id = ?";
	public static final String SQL_DELETE_STUDENT = "DELETE FROM students WHERE student_id = ?";
	public static final String SQL_DELETE_STOP = "DELETE FROM stops WHERE stop_id = ?";
	public static final String SQL_DELETE_ROUTE = "DELETE FROM routes WHERE route_id = ?";
	public static final String SQL_DEATTACH_STOP_FROM_ROUTE = "DELETE FROM stops_routes WHERE stop_id = ? AND route_id = ?";
	
	public static final String SQL_CLEAR_STOPS = "DELETE FROM stops_routes WHERE route_id = ?";
	
	/* OTHER QUERIES */

	public static final String SQL_SELECT_STOP = "SELECT * FROM stops WHERE lat = ? AND lng = ?";
			
}
