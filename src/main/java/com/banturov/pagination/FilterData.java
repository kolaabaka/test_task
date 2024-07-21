package com.banturov.pagination;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Pagination entity")
public class FilterData {
	@Schema(description = "Required to filter entries, must be not empty", example = "dd.mm.yy")
	private String filterDate;
	@Schema(description = "Required to filter entries, must be not empty", minimum = "3")
	private String filterDeparture;
	@Schema(description = "Required to filter entries, must be not empty", minimum = "3")
	private String filterDestination;
	@Schema(description = "Required to filter entries, must be not empty", minimum = "3")
	private String filterCariier;

	public FilterData(String filterDate, String filterDeparture, String filterDestination, String filterCariier) {
		super();
		this.filterDate = filterDate;
		this.filterDeparture = filterDeparture;
		this.filterDestination = filterDestination;
		this.filterCariier = filterCariier;
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
