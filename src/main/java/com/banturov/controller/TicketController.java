package com.banturov.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banturov.entity.BuyTicket;
import com.banturov.entity.PurchaseFilter;
import com.banturov.entity.Ticket;
import com.banturov.pagination.Page;
import com.banturov.repository.TicketRepository;

@RestController
@RequestMapping(produces = "application/json")
public class TicketController {

	private static String url = "jdbc:mysql://localhost:3306/test";
	private static String userName = "root";
	private static String password = "root";

	@Autowired
	private TicketRepository rep;

	@GetMapping(path = "/show/freeTicket")
	public ResponseEntity<List<Ticket>> getTicket(@RequestBody Page page) {
		List<Ticket> resultList = rep.getFreeTicket(url, userName, password, page);
		if (!resultList.isEmpty())
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}

	@GetMapping(path = "/filter/ticket/carrier")
	public ResponseEntity<List<Ticket>> filterCarrier(@RequestBody Page page) {
		List<Ticket> resultList = rep.getTicketFilterCarrier(url, userName, password, page);
		if (!resultList.isEmpty())
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}

	@GetMapping(path = "/filter/ticket/date")
	public ResponseEntity<List<Ticket>> filterDate(@RequestBody Page page) {
		List<Ticket> resultList = rep.getTicketFilterDate(url, userName, password, page);
		if (!resultList.isEmpty())
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}

	@GetMapping(path = "/filter/ticket/departure")
	public ResponseEntity<List<Ticket>> filterDeparture(@RequestBody Page page) {
		List<Ticket> resultList = rep.getTicketFilterDeparture(url, userName, password, page);
		if (!resultList.isEmpty())
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}

	@GetMapping(path = "/filter/ticket/destination")
	public ResponseEntity<List<Ticket>> filterDestination(@RequestBody Page page) {
		List<Ticket> resultList = rep.getTicketFilterDestination(url, userName, password, page);
		if (!resultList.isEmpty())
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}

	@GetMapping(path = "/filter/ticket/purchased")
	public ResponseEntity<List<Ticket>> filterPurchased(@RequestBody PurchaseFilter PurchaseFilter) {
		List<Ticket> resultList = rep.showPurchasedTicket(url, userName, password, PurchaseFilter);
		if (!resultList.isEmpty())
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}

	@PatchMapping(path = "/ticket/buy")
	public ResponseEntity<String> buyTicket(@RequestBody BuyTicket buyTicket) {
		try {
			String status = rep.buyTicket(url, userName, password, buyTicket);
			return new ResponseEntity<String>(status, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
