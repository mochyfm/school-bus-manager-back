package com.transportessalesianoslc.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.transportessalesianoslc.models.BusRoute;
import com.transportessalesianoslc.models.BusStop;
import com.transportessalesianoslc.models.Message;
import com.transportessalesianoslc.models.Student;
import com.transportessalesianoslc.repository.querys.RepositoryQuerys;

@Repository
public class TransportRepositoryImpl implements TransportRepository  {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private List<BusStop> getFilterStops(List<BusStop> stopsToFilter) {

		List<BusStop> listToReturn = new ArrayList<>();
		List<BusStop> currentStops = getAllStops();

		try {

			for (Iterator dataBaseIterator = currentStops.iterator(); dataBaseIterator.hasNext();) {
				Boolean exists = false;
				BusStop DataBaseBusStop = (BusStop) dataBaseIterator.next();
				for (Iterator outsetIterator = stopsToFilter.iterator(); outsetIterator.hasNext();) {
					BusStop outBusStop = (BusStop) outsetIterator.next();
					if (Double.compare(DataBaseBusStop.getLat(),outBusStop.getLat()) == 0 && 
							Double.compare(DataBaseBusStop.getLng(), outBusStop.getLng()) == 0) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					jdbcTemplate.update(RepositoryQuerys.SQL_DELETE_STOP, DataBaseBusStop.getStop_id());
				}	
			}

			for (Iterator<BusStop> iterator = stopsToFilter.iterator(); iterator.hasNext();) {
				BusStop busStop = (BusStop) iterator.next();
				List<BusStop> existingStops = jdbcTemplate.query(
						RepositoryQuerys.SQL_SELECT_STOP, 
						BeanPropertyRowMapper.newInstance(BusStop.class), 
						new Object[] { 
								busStop.getLat(), 
								busStop.getLng() }

						);
				if (existingStops.isEmpty()) {
					listToReturn.add(busStop);
				} 
			}

			return listToReturn;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int setStops(List<BusStop> stops) {
		List<BusStop> innerStops = getFilterStops(stops);	
		try {
			for (Iterator<BusStop> iterator = innerStops.iterator(); iterator.hasNext();) {
				BusStop stop = (BusStop) iterator.next();
				jdbcTemplate.update(
						RepositoryQuerys.SQL_INSERT_STOP,
						new Object[] { 
								stop.getLat(),
								stop.getLng()
						} 
						);
			}
			return 1;
		} catch (Exception ex) {
			return 0;
		}

	}

	@Override
	public List<BusStop> getAllStops() {
		List<BusStop> stops = jdbcTemplate.query(
				RepositoryQuerys.SQL_SELECT_ALL_STOPS, 
				BeanPropertyRowMapper.newInstance(BusStop.class));
		return stops;
	}

	@Override
	public List<BusRoute> getAllRoutes() {
		List<BusRoute> routes = jdbcTemplate.query(
				RepositoryQuerys.SQL_SELECT_ALL_ROUTES, 
				BeanPropertyRowMapper.newInstance(BusRoute.class));
		routes = getRelatedStops(routes);
		return routes;
	}

	private List<BusRoute> getRelatedStops(List<BusRoute> routes) {
		List<BusRoute> outputRoutes = new ArrayList<>();
		for (Iterator<BusRoute> iterator = routes.iterator(); iterator.hasNext();) {
			BusRoute route = (BusRoute) iterator.next();
			List<BusStop> stops=  jdbcTemplate.query(
					RepositoryQuerys.SQL_SELECT_RELATED_STOPS, 
					BeanPropertyRowMapper.newInstance(BusStop.class), route.getRoute_id());
			route.setStops(stops);
			outputRoutes.add(route);
		} 
		return outputRoutes;
	}

	@Override
	public List<List<BusStop>> getAllAsignedStops() {
		List<BusRoute> routes = getAllRoutes();
		List<List<BusStop>> stopsAssigned = new ArrayList<>();
		for (Iterator<BusRoute> iterator = routes.iterator(); iterator.hasNext();) {
			BusRoute route = (BusRoute) iterator.next();
			stopsAssigned.add(jdbcTemplate.query(
					RepositoryQuerys.SQL_SELECT_RELATED_STOPS, 
					BeanPropertyRowMapper.newInstance(BusStop.class), route.getRoute_id()));

		}
		return stopsAssigned;
	}

	private List<BusStop> getAsignedStops(Long route_id) {
		return jdbcTemplate.query(
				RepositoryQuerys.SQL_SELECT_RELATED_STOPS, 
				BeanPropertyRowMapper.newInstance(BusStop.class), route_id);
	}

	@Override
	public BusRoute getRoute(Long route_id) {
		BusRoute route = jdbcTemplate.queryForObject(
				RepositoryQuerys.SQL_SELECT_ROUTE_BY_ID, 
				BeanPropertyRowMapper.newInstance(BusRoute.class), route_id);
		route.setStops(jdbcTemplate.query(
				RepositoryQuerys.SQL_SELECT_RELATED_STOPS, 
				BeanPropertyRowMapper.newInstance(BusStop.class), route_id));
		return route;	
	}

	@Override
	public int newBusRoute(BusRoute route) {
		try {
			return jdbcTemplate.update(
					RepositoryQuerys.SQL_INSERT_ROUTE,
					new Object[] { 
							route.getLabel(),
							route.getRoute_type(),
					} 
					);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int removeBusRoute(Long route_id) {
		return jdbcTemplate.update(RepositoryQuerys.SQL_DELETE_ROUTE, route_id);
	}
	
	@Override
	public BusStop getStop(Long stop_id) {
		try {
			return jdbcTemplate.queryForObject(
					RepositoryQuerys.SQL_SELECT_STOP_BY_ID, 
					BeanPropertyRowMapper.newInstance(BusStop.class), stop_id);
		} catch (Exception e) {
			return null;
		}
		
	}

	@Override
	public int assignStopsToRoute(Long route_id, List<BusStop> stops) {
		
		List<BusStop> assignedStops = getAsignedStops(route_id);
		List<BusStop> toAdd = new ArrayList<>();
		
		try {
			
			jdbcTemplate.update(RepositoryQuerys.SQL_CLEAR_STOPS, route_id);
			for (Iterator<BusStop> stopsForRoute = stops.iterator(); stopsForRoute.hasNext();) {
				BusStop busStop = (BusStop) stopsForRoute.next();
				jdbcTemplate.update(RepositoryQuerys.SQL_ASSIGN_STOP, route_id, busStop.getStop_id());
			}

			return 1;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}


	@Override
	public List<BusStop> getUnassignedStops() {
		return jdbcTemplate.query(
				RepositoryQuerys.SQL_SELECT_NON_RELATED_STOPS, 
				BeanPropertyRowMapper.newInstance(BusStop.class));
	}

	public List<BusRoute> getStopsAssigned(List<BusRoute> routes, Long student_id) {
		List<BusRoute> outputRoutes = routes;
		for (Iterator<BusRoute> iterator = outputRoutes.iterator(); iterator.hasNext();) {
			BusRoute route = (BusRoute) iterator.next();
			List<BusStop> stops=  jdbcTemplate.query(
					RepositoryQuerys.SQL_SELECT_RELATED_STOPS_TO_STUDENT_ON_ROUTE, 
					BeanPropertyRowMapper.newInstance(BusStop.class), route.getRoute_id(), student_id);
			
			route.setStops(stops);
		} 
		return outputRoutes;
	}
	
	@Override
	public List<BusRoute> getAssignedRoutesFromStudent(Long student_id) {
		List<BusRoute> routes = jdbcTemplate.query(
				RepositoryQuerys.SQL_SELECT_ROUTES_FROM_STUDENT, 
				BeanPropertyRowMapper.newInstance(BusRoute.class), student_id);
		return getStopsAssigned(routes, student_id);
	}

	@Override
	public List<BusRoute> getNonAssignedRoutesFromStudent(Long student_id) {
		List<BusRoute> routes = jdbcTemplate.query(
				RepositoryQuerys.SQL_SELECT_NON_RELATED_ROUTES_FROM_STUDENT, 
				BeanPropertyRowMapper.newInstance(BusRoute.class), student_id);
		return getRelatedStops(routes);
	}

	@Override
	public int assignStopToStudent(Long route_id, Long stop_id, Long student_id) {
		return jdbcTemplate.update(
				RepositoryQuerys.SQL_ASSIGN_STUDENT_TO_ROUTE, 
				new Object[] {
						route_id,
						stop_id,
						student_id
				});
	}

}
