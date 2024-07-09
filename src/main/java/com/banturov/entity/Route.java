package com.banturov.entity;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

public class Route {

	@Schema(description = "Departure trip")
	private String departure;
	@Schema(description = "Destination trip")
	private String destination;
	@Schema(description = "SQL ID carrier")
	private Integer carrierId;
	@Schema(description = "Duration of the trip")
	private Integer duration;

	public Route() {
	}

	public Route(String departure, String destination, Integer carrierId, Integer duration) {
		this.departure = departure;
		this.destination = destination;
		this.carrierId = carrierId;
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

	public Integer getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(Integer carrierId) {
		this.carrierId = carrierId;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Override
	public int hashCode() {
		return Objects.hash(carrierId, departure, destination, duration);
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
		return Objects.equals(carrierId, other.carrierId) && Objects.equals(departure, other.departure)
				&& Objects.equals(destination, other.destination) && Objects.equals(duration, other.duration);
	}

	@Override
	public String toString() {
		return "Route [departure=" + departure + ", destination=" + destination + ", carrierId=" + carrierId
				+ ", duration=" + duration + "]";
	}

}
