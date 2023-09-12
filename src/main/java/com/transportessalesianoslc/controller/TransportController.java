package com.transportessalesianoslc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transportessalesianoslc.models.BusRoute;
import com.transportessalesianoslc.models.BusStop;
import com.transportessalesianoslc.models.Message;
import com.transportessalesianoslc.services.JDBCServicesImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/tslc")
public class TransportController {

	@Autowired
	JDBCServicesImpl genServices;
	
	@GetMapping("/stop")
	public ResponseEntity<BusStop> getStopById(@RequestParam Long id) {
		try {
			
			BusStop stop = genServices.getStopById(id);
			return stop == null
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(stop, HttpStatus.OK);
			
		} catch (Exception excep) {
			System.out.println(excep);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/stops")
	public ResponseEntity<List<BusStop>> getAllStops() {
		try {
			
			List<BusStop> stops = new ArrayList<BusStop>();
			genServices.getAllStops().forEach(stops::add);
			return stops.isEmpty() 
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(stops, HttpStatus.OK);
			
		} catch (Exception excep) {
			System.out.println(excep);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/stops/not-assigned")
	public ResponseEntity<List<BusStop>> getNonAssignedStops() {
		try {
			
			List<BusStop> unassignedStops = new ArrayList<BusStop>();
			genServices.getUnassignedStops().forEach(unassignedStops::add);
			return unassignedStops.isEmpty() 
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(unassignedStops, HttpStatus.OK);
			
		} catch (Exception excep) {
			System.out.println(excep);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/stops")
	public ResponseEntity<String> importStops(@RequestBody List<BusStop> stops) {
		try {
			return genServices.newStops(stops)
					? new ResponseEntity<>("New stops imported successfully", HttpStatus.CREATED)
					: new ResponseEntity<>("Could not import stops", HttpStatus.I_AM_A_TEAPOT);
		} catch (Exception e) {
			return new ResponseEntity<>("An Error has ocurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/assign")
	public ResponseEntity<String> importStops(@RequestParam Long routeId, Long stopId, Long studentId) {
		try {
			return genServices.assignRouteToStudent(routeId, stopId, studentId)
					? new ResponseEntity<>("Asignes succesfully", HttpStatus.CREATED)
					: new ResponseEntity<>("Could not assign stop from route", HttpStatus.I_AM_A_TEAPOT);
		} catch (Exception e) {
			return new ResponseEntity<>("An Error has ocurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/routes")
	public ResponseEntity<List<BusRoute>> getAllRoutes() {
		try {
			List<BusRoute> routes = new ArrayList<BusRoute>();
			genServices.getAllRoutes().forEach(routes::add);
			return routes.isEmpty() 
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(routes, HttpStatus.OK);
			
		} catch (Exception excep) {
			System.out.println(excep);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/routes/student")
	public ResponseEntity<List<BusRoute>> getNonAssignedRoutesFromStudent(@RequestParam Long id) {
		try {
			
			List<BusRoute> routes = new ArrayList<BusRoute>();
			genServices.getNonAssignedRoutesFromStudent(id).forEach(routes::add);
			return routes.isEmpty() 
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(routes, HttpStatus.OK);
			
		} catch (Exception excep) {
			System.out.println(excep);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/student/{id}/routes")
	public ResponseEntity<List<BusRoute>> getAssignedRoutesFromStudent(@PathVariable("id") Long student_id)  {
		try {
			List<BusRoute> routes = new ArrayList<BusRoute>();
			genServices.getAssignedRoutesFromStudent(student_id).forEach(routes::add);
			return routes.isEmpty() 
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(routes, HttpStatus.OK);
			
		} catch (Exception excep) {
			System.out.println(excep);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/routes/stops")
	public ResponseEntity<List<List<BusStop>>> getAllAsignedStops() {
		try {
			
			List<List<BusStop>> stops = new ArrayList<>();
			genServices.getAllAsignedStops().forEach(stops::add);
			return stops.isEmpty() 
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(stops, HttpStatus.OK);
			
		} catch (Exception excep) {
			System.out.println(excep);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/route")
	public ResponseEntity<BusRoute> getRouteById(@RequestParam Long id) {
		try {
			BusRoute route = genServices.getRoute(id);
				return route != null
					? new ResponseEntity<>(route, HttpStatus.CREATED)
					: new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	@DeleteMapping("/route")
	public ResponseEntity<String> deleteRoute(@RequestParam Long id) {
		try {
			return genServices.removeRoute(id)
					? new ResponseEntity<>("Route " + id + " deleted successfully", HttpStatus.CREATED)
					: new ResponseEntity<>("Route doesnt exist", HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>("An Error has ocurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/route")
	public ResponseEntity<String> assignStopsToRoute(@RequestParam Long id, @RequestBody List<BusStop> stops) {
		try {
			return genServices.assignStopsToRoute(id, stops)
					? new ResponseEntity<>("Stops assigned to route " + id, HttpStatus.OK)
					: new ResponseEntity<>("Route doesnt exist", HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>("An Error has ocurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/route/msg")
	public ResponseEntity<String> sendGlobalMessage(@RequestParam Long id, @RequestBody Message message) {
		try {
			return genServices.globalMessage(id, new Message(message.getMessage(), message.getMessage_type()))
					? new ResponseEntity<>("Message sent", HttpStatus.ACCEPTED)
					: new ResponseEntity<>("Could not send message", HttpStatus.NOT_IMPLEMENTED);
		} catch (Exception e) {
			return new ResponseEntity<>("An Error has ocurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/route/new")
	public ResponseEntity<String> newRoute(@RequestBody BusRoute route) {
		try {
			return genServices.newRoute(new BusRoute(route.getLabel(), route.getRoute_type()))
					? new ResponseEntity<>("New route created successfully", HttpStatus.CREATED)
					: new ResponseEntity<>("Route Label already exists", HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>("An Error has ocurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
