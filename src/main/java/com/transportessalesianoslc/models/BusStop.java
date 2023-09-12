package com.transportessalesianoslc.models;

public class BusStop {

	private Long stop_id;
	private Double lat;
	private Double lng;
	
	public BusStop() {
		
	}
	
	public BusStop(Double lat, Double lng) {
		this.lat = lat;
		this.lng = lng;
	}
	
	public BusStop(Long stop_id, String direction, Double lat, Double lng) {
		this.stop_id = stop_id;
		this.lat = lat;
		this.lng = lng;
	}

	public Long getStop_id() {
		return stop_id;
	}

	public void setStop_id(Long stop_id) {
		this.stop_id = stop_id;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return "BusStop [stop_id=" + stop_id + ", lat=" + lat + ", lng=" + lng + "]\n";
	}	
	
	

}
