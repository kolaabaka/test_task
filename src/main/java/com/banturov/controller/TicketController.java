package com.banturov.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Level;

import com.banturov.configuration.PropertiesConfig;
import com.banturov.entity.BuyTicket;
import com.banturov.entity.PurchaseFilter;
import com.banturov.entity.Ticket;
import com.banturov.jwt.JwtConverter;
import com.banturov.kafka.KafkaTicketProducer;
import com.banturov.pagination.FilterData;
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

	@Autowired
	private JwtConverter jwt;

	private static Logger log = Logger.getLogger(TicketController.class.getName());

	@Operation(summary = "Shows all tickets not purchased", description = "Show all not purchased tickets")
	@GetMapping(path = "/show/freeTicket")
	public ResponseEntity<List<Ticket>> getTicket(
			@Parameter(required = true, example = "Minimal value 1") @RequestParam(name = "limit") int limit,
			@Parameter(required = true, example = "Minimal value 1") @RequestParam(name = "page") int page) {
		List<Ticket> resultList = rep.getFreeTicket(url, userName, password, limit, page);
		if (!resultList.isEmpty())
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}

	@Operation(summary = "Shows filtered tickets")
	@GetMapping(path = "/ticket/filter")
	public ResponseEntity<List<Ticket>> filterDestination(
			@RequestBody @Parameter(required = true, example = "London") FilterData filterData,
			@Parameter(required = true, example = "Minimal value 1") @RequestParam(name = "limit") int limit,
			@Parameter(required = true, example = "Minimal value 1") @RequestParam(name = "page") int page)
			throws Exception {
		List<Ticket> resultList = new ArrayList<>();
		try {
			resultList = rep.getTicketFilterDestination(url, userName, password, filterData, limit, page);
			if (!resultList.isEmpty())
				return new ResponseEntity<>(resultList, HttpStatus.OK);
			else
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		} catch (SQLException e) {
			log.log(Level.WARNING, e.getMessage());
			resultList.add(new Ticket(e.getMessage()));
			return new ResponseEntity<>(resultList, HttpStatus.SERVICE_UNAVAILABLE);
		} catch (IllegalArgumentException e) {
			resultList.add(new Ticket(e.getMessage()));
			log.log(Level.WARNING, e.getMessage());
			return new ResponseEntity<>(resultList, HttpStatus.valueOf(400));
		}

	}

	@Operation(summary = "Shows all purchased tickets by certain user")
	@PostMapping(path = "/filter/ticket/purchased")
	public ResponseEntity<List<Ticket>> filterPurchased(@RequestBody @Parameter(required = true) String jwtToken,
			@Parameter(required = true, example = "Minimal value 1") @RequestParam(name = "limit") int limit,
			@Parameter(required = true, example = "Minimal value 0") @RequestParam(name = "page") int page)
			throws Exception {
		List<Ticket> resultList = new ArrayList<>();
		try {
			resultList = rep.showPurchasedTicket(url, userName, password, jwt.jwtDecoderLogin(jwtToken), limit, page);
			if (!resultList.isEmpty())
				return new ResponseEntity<>(resultList, HttpStatus.OK);
			else
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		} catch (SQLException e) {
			log.log(Level.WARNING, e.getMessage());
			resultList.add(new Ticket(e.getMessage()));
			return new ResponseEntity<>(resultList, HttpStatus.SERVICE_UNAVAILABLE);
		} catch (IllegalArgumentException e) {
			log.log(Level.WARNING, e.getMessage());
			resultList.add(new Ticket(e.getMessage()));
			return new ResponseEntity<>(resultList, HttpStatus.valueOf(400));
		}

	}

	@Operation(summary = "Allow buy ticket", description = "")
	@PatchMapping(path = "/ticket/buy")
	public ResponseEntity<String> buyTicket(@RequestBody @Parameter(required = true) int ticketId,
			@RequestBody @Parameter(required = true) String jwtToken) {
		try {
			String status = rep.buyTicket(url, userName, password, ticketId, jwt.jwtDecoderLogin(jwtToken));
			kf.sendMessage(rep.findTicketById(url, userName, password, ticketId));
			log.log(Level.INFO, "User " + jwt.jwtDecoderLogin(jwtToken) + " bought a ticket");
			return new ResponseEntity<String>(status, HttpStatus.OK);
		} catch (Exception e) {
			log.log(Level.WARNING, e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
		}
	}
}
