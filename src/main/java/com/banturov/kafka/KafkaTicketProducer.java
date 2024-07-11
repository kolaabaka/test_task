package com.banturov.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaTicketProducer {

	@Autowired
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	public KafkaTicketProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage() {
		kafkaTemplate.send("ticket_backup", "HELLO MAZAFAKA");
	}
}
