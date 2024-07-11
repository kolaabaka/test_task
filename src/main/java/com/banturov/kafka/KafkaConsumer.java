package com.banturov.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

	@KafkaListener(topics = "test", groupId = "aad")
	public void listen(String message) {
		System.out.println(message);
	}
}
