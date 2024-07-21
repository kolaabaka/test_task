package com.banturov.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public class BuyTicket {

	@Schema(description = "SQL ID ticket")
	private Integer ticket_id;

	public Integer getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(Integer ticket_id) {
		this.ticket_id = ticket_id;
	}

}
