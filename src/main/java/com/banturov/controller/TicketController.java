package com.banturov.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banturov.configuration.PropertiesConfig;
import com.banturov.entity.BuyTicket;
import com.banturov.entity.PurchaseFilter;
import com.banturov.entity.Ticket;
import com.banturov.kafka.KafkaTicketProducer;
import com.banturov.pagination.Page;
import com.banturov.repository.TicketRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Ticket controller", description = "Allow check and buy tickets ")
@RestController
@RequestMapping(produces = "application/json")
public class TicketController {

	private static String url = null;
	private static String userName = null;
	private static String password = null;

	static {
		url = PropertiesConfig.get("db.url");
		userName = PropertiesConfig.get("db.login");
		password = PropertiesConfig.get("db.password");
	}
	@Autowired
	private KafkaTicketProducer kf;

	@Autowired
	private TicketRepository rep;

	@Operation(summary = "Shows all tickets not purchased", description = "Show all not purchased tickets")
	@GetMapping(path = "/show/freeTicket")
	public ResponseEntity<List<Ticket>> getTicket(@RequestBody @Parameter(required = true) Page page) {
		List<Ticket> resultList = rep.getFreeTicket(url, userName, password, page);
		if (!resultList.isEmpty())
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}

	@Operation(summary = "Shows all tickets filtered by destination")
	@GetMapping(path = "/ticket/filter")
	public ResponseEntity<List<Ticket>> filterDestination(
			@RequestBody @Parameter(required = true, example = "London") Page page) throws Exception {
		List<Ticket> resultList = new ArrayList<>();
		;
		try {
			resultList = rep.getTicketFilterDestination(url, userName, password, page);
			if (!resultList.isEmpty())
				return new ResponseEntity<>(resultList, HttpStatus.OK);
			else
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		} catch (SQLException e) {
			return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
		} catch (IllegalArgumentException e) {
			Ticket tik = new Ticket(e.getMessage());
			resultList.add(tik);
			return new ResponseEntity<>(resultList, HttpStatus.valueOf(400));
		}

	}

	@Operation(summary = "Shows all purchased tickets by certain user")
	@GetMapping(path = "/filter/ticket/purchased")
	public ResponseEntity<List<Ticket>> filterPurchased(
			@RequestBody @Parameter(required = true) PurchaseFilter PurchaseFilter) throws Exception {
		List<Ticket> resultList;
		try {
			resultList = rep.showPurchasedTicket(url, userName, password, PurchaseFilter);
			if (!resultList.isEmpty())
				return new ResponseEntity<>(resultList, HttpStatus.OK);
			else
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		} catch (SQLException e) {
			return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(null, HttpStatus.valueOf(400));
		}

	}

	@Operation(summary = "Allow buy ticket", description = "")
	@PatchMapping(path = "/ticket/buy")
	public ResponseEntity<String> buyTicket(@RequestBody @Parameter(required = true) BuyTicket buyTicket) {
		try {
			String status = rep.buyTicket(url, userName, password, buyTicket);
			kf.sendMessage(rep.findTicketById(url, userName, password, buyTicket.getTicket_id()));
			return new ResponseEntity<String>(status, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
		}
	}
}
