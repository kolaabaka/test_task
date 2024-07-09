package com.banturov.pagination;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Pagination entity")
public class Page {
	@Schema(description = "Number of page, must be not empty and more than -1, start value 0")
	private int page;
	@Schema(description = "Limit of entries per page, must be not empty and more than 0")
	private int limit;
	@Schema(description = "Required to filter entries, must be not empty")
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
