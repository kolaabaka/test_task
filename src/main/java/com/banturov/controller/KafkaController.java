package com.banturov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banturov.kafka.KafkaTicketProducer;

@RestController
public class KafkaController {

	@Autowired
	private final KafkaTicketProducer kf;
	
	public KafkaController(KafkaTicketProducer kf) {
		this.kf = kf;
	}
	
	@PostMapping("/kf")
	public String Kafka() {
		kf.sendMessage();
		return "true";
	}
}
