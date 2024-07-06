package com.banturov.entity;

import java.util.Objects;

public class Route {

	private String departure;
	private String destination;
	private String route;
	private Integer duration;

	public Route() {
	}

	public Route(String departure, String destination, String route, Integer duration) {
		super();
		this.departure = departure;
		this.destination = destination;
		this.route = route;
		this.duration = duration;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Override
	public int hashCode() {
		return Objects.hash(departure, destination, duration, route);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		return Objects.equals(departure, other.departure) && Objects.equals(destination, other.destination)
				&& Objects.equals(duration, other.duration) && Objects.equals(route, other.route);
	}

	@Override
	public String toString() {
		return "Route [departure=" + departure + ", destination=" + destination + ", route=" + route + ", duration="
				+ duration + "]";
	}

}
