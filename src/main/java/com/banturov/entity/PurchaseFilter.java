package com.banturov.entity;

import com.banturov.pagination.Page;

import io.swagger.v3.oas.annotations.media.Schema;

public class PurchaseFilter {

	@Schema(name = "Pagination entity")
	private Page page;
	@Schema(name = "Buyer entity")
	private User user;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
