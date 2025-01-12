package com.banturov.entity;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User entiry")
public class User {

	@Schema(description = "User login, must be unique and no empty", example = "string")
	private String login;
	@Schema(description = "User password, must be not empty", example = "string")
	private String password;
	@Schema(description = "User name", example = "string")
	private String name;

	public User() {
	}

	public User(String login, String password, String name) {
		super();
		this.login = login;
		this.password = password;
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(login, name, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(login, other.login) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + ", name=" + name + "]";
	}

}
