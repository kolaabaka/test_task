package com.banturov.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banturov.entity.Purchase;
import com.banturov.entity.Ticket;
import com.banturov.entity.User;
import com.banturov.pagination.Page;
import com.banturov.repository.TicketRepository;
import com.banturov.repository.UserRepository;

@RestController
@RequestMapping(produces = "application/json")
public class TicketController {

	private static String url = "jdbc:mysql://localhost:3306/test";
	private static String userName = "root";
	private static String password = "root";

	@GetMapping(path = "/show/freeTicket")
	public ResponseEntity<List<Ticket>> getTicket(@RequestBody Page page) {
		List<Ticket> resultList = TicketRepository.getFreeTicket(url, userName, password, page);
		if (!resultList.isEmpty())
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}

	@GetMapping(path = "/filter/ticket/carrier")
	public ResponseEntity<List<Ticket>> filterCarrier(@RequestBody Page page) {
		List<Ticket> resultList = TicketRepository.getTicketFilterCarrier(url, userName, password, page);
		if (!resultList.isEmpty())
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}

	@GetMapping(path = "/filter/ticket/date")
	public ResponseEntity<List<Ticket>> filterDate(@RequestBody Page page) {
		List<Ticket> resultList = TicketRepository.getTicketFilterDate(url, userName, password, page);
		if (!resultList.isEmpty())
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}

	@GetMapping(path = "/filter/ticket/departure")
	public ResponseEntity<List<Ticket>> filterDeparture(@RequestBody Page page) {
		List<Ticket> resultList = TicketRepository.getTicketFilterDeparture(url, userName, password, page);
		if (!resultList.isEmpty())
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}

	@GetMapping(path = "/filter/ticket/destination")
	public ResponseEntity<List<Ticket>> filterDestination(@RequestBody Page page) {
		List<Ticket> resultList = TicketRepository.getTicketFilterDestination(url, userName, password, page);
		if (!resultList.isEmpty())
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}

	@GetMapping(path = "/filter/ticket/purchased")
	public ResponseEntity<List<Ticket>> filterPurchased(@RequestBody Purchase purchase ) {
		List<Ticket> resultList = TicketRepository.showPurchasedTicket(url, userName, password, purchase);
		if (!resultList.isEmpty())
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}
}
