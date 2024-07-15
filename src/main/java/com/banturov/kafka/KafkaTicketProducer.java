package com.banturov.kafka;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.banturov.entity.Ticket;

@Service
public class KafkaTicketProducer {

	Logger log = Logger.getLogger(KafkaTicketProducer.class.getName());

	@Autowired
	KafkaTemplate<String, Ticket> kafkaTemplate;

	private static final String TOPIC = "ticket_backup";

	public void sendMessage(Ticket ticket) {
		kafkaTemplate.send(TOPIC, ticket);
		log.log(Level.INFO, "Kafka producer send: " + ticket);
	}
}
