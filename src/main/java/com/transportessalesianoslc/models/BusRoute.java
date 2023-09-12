package com.transportessalesianoslc.models;

import java.util.List;

public class BusRoute {
	
	private Long route_id;
	private String label;
	private String route_type;
	private List<BusStop> stops;
	
	public BusRoute() {
		// TODO Auto-generated constructor stub
	}
	
	public BusRoute(String label, String route_type) {
		this.label = label;
		this.route_type = route_type;
	}

	public BusRoute(Long route_id, String label, 
			String route_type, List<BusStop> stops) {
		this.route_id = route_id;
		this.label = label;
		this.route_type = route_type;
		this.stops = stops;
	}

	public Long getRoute_id() {
		return route_id;
	}

	public void setRoute_id(Long route_id) {
		this.route_id = route_id;
	}

	public List<BusStop> getStops() {
		return stops;
	}

	public void setStops(List<BusStop> stops) {
		this.stops = stops;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getRoute_type() {
		return route_type;
	}

	public void setRoute_type(String route_type) {
		this.route_type = route_type;
	}

	@Override
	public String toString() {
		return "BusRoute [route_id=" + route_id + ", label=" + label + ", route_type=" + route_type + ", stops=" + stops + "]";
	}
	
	
	
}
