package com.banturov.entity;

import com.banturov.pagination.Page;

public class Purchase {

	private Page page;
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
