package com.banturov.entity;

import java.util.Objects;

public class Ticket {

	private Integer routeId;
	private Integer buyerId;
	private Integer placeNumber;
	private Integer price;
	private String date;
	public Ticket(Integer routeId, Integer buyerId, Integer placeNumber, Integer price, String date) {
		super();
		this.routeId = routeId;
		this.buyerId = buyerId;
		this.placeNumber = placeNumber;
		this.price = price;
		this.date = date;
	}
	public Ticket() {
	}
	public Integer getRouteId() {
		return routeId;
	}
	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getPlaceNumber() {
		return placeNumber;
	}
	public void setPlaceNumber(Integer placeNumber) {
		this.placeNumber = placeNumber;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public int hashCode() {
		return Objects.hash(buyerId, date, placeNumber, price, routeId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return Objects.equals(buyerId, other.buyerId) && Objects.equals(date, other.date)
				&& Objects.equals(placeNumber, other.placeNumber) && Objects.equals(price, other.price)
				&& Objects.equals(routeId, other.routeId);
	}
	@Override
	public String toString() {
		return "Ticket [routeId=" + routeId + ", buyerId=" + buyerId + ", placeNumber=" + placeNumber + ", price="
				+ price + ", date=" + date + "]";
	}

	
}
