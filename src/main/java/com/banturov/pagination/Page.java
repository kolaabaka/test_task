package com.banturov.pagination;

public class Page {
	private int page;
	private int limit;

	public Page(int page, int limit) {
		this.page = page;
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
