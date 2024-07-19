package com.banturov.pagination;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Pagination entity")
public class Page {
	@Schema(description = "Number of page, must be not empty and more than -1, start value 0")
	private int page;
	@Schema(description = "Limit of entries per page, must be not empty and more than 0")
	private int limit;
	@Schema(description = "Required to filter entries, must be not empty", example = "dd.mm.yy")
	private String filterDate;
	@Schema(description = "Required to filter entries, must be not empty", minimum = "3")
	private String filterDeparture;
	@Schema(description = "Required to filter entries, must be not empty", minimum = "3")
	private String filterDestination;
	@Schema(description = "Required to filter entries, must be not empty", minimum = "3")
	private String filterCariier;

	public Page(int page, int limit, String filterDate, String filterDeparture, String filterDestination,
			String filterCariier) {
		super();
		this.page = page;
		this.limit = limit;
		this.filterDate = filterDate;
		this.filterDeparture = filterDeparture;
		this.filterDestination = filterDestination;
		this.filterCariier = filterCariier;
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

	public String getFilterDate() {
		return filterDate;
	}

	public void setFilterDate(String filterDate) {
		this.filterDate = filterDate;
	}

	public String getFilterDeparture() {
		return filterDeparture;
	}

	public void setFilterDeparture(String filterDeparture) {
		this.filterDeparture = filterDeparture;
	}

	public String getFilterDestination() {
		return filterDestination;
	}

	public void setFilterDestination(String filterDestination) {
		this.filterDestination = filterDestination;
	}

	public String getFilterCariier() {
		return filterCariier;
	}

	public void setFilterCariier(String filterCariier) {
		this.filterCariier = filterCariier;
	}

}
