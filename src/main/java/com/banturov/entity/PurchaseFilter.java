package com.banturov.entity;


import io.swagger.v3.oas.annotations.media.Schema;

public class PurchaseFilter {


	@Schema(name = "Buyer entity")
	private User user;



	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
