package com.banturov.entity;

import java.util.Objects;

public class Сarrier {

	private String name;
	private String number;

	public Сarrier() {
	}

	public Сarrier(String name, String number) {
		super();
		this.name = name;
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Сarrier other = (Сarrier) obj;
		return Objects.equals(name, other.name) && Objects.equals(number, other.number);
	}

	@Override
	public String toString() {
		return "Сarrier [name=" + name + ", number=" + number + "]";
	}

}
