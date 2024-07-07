package com.banturov.pagination;

public class Page {
	private int page;
	private int limit;
	private String filterValue;

	public Page(int page, int limit, String filterValue) {
		this.page = page;
		this.limit = limit;
		this.filterValue = filterValue;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

}
