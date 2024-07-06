package com.banturov.entity;

import java.util.Objects;

public class Ticket {

	private String name;
	private Integer age;
	
	public Ticket(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, name);
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
		return Objects.equals(age, other.age) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Ticket [name=" + name + ", age=" + age + "]";
	}
	
	
}
