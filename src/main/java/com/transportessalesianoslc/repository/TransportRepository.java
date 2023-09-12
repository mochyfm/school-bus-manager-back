package com.transportessalesianoslc.repository;

import java.util.List;

import com.transportessalesianoslc.models.BusRoute;
import com.transportessalesianoslc.models.BusStop;
import com.transportessalesianoslc.models.Message;
import com.transportessalesianoslc.models.Student;

/**
 * 
 * Package realized for the "TransportRepository", located on the Back-End of the
 * Transport management application. This interface has all the methods that are required
 * on such section, and they are not related with the ClientRepository.
 * 
 * @see TransportRepositoryImpl
 * @author Oceánida S.L
 *
 */

public interface TransportRepository {
	
	/* HTTP - GET VERB (METHODS) */
	
	public List<BusStop> getAllStops();
	public List<BusRoute> getAllRoutes();
	public List<List<BusStop>> getAllAsignedStops();
	public List<BusStop> getUnassignedStops();
	
	public List<BusRoute> getAssignedRoutesFromStudent(Long student_id);
	public List<BusRoute> getNonAssignedRoutesFromStudent(Long student_id);
	
	/* HTTP - PUT VERB (METHODS) */ 
		
	public BusStop getStop(Long stop_id);
	public BusRoute getRoute(Long route_id);
	
	/* HTTP - DELETE VERB (METHODS) */
	
	public int removeBusRoute(Long route_id);
	
	/* HTTP - POST VERB (METHODS) */
	
	public int setStops(List<BusStop> stops);
	public int newBusRoute(BusRoute route);
	public int assignStopsToRoute(Long route_id, List<BusStop> stops);
	public int assignStopToStudent(Long route_id, Long stop_id, Long student_id);
	
	/* HTTP - PUT VERB (METHODS) */
	
	
	
}

