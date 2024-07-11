package com.banturov.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

	@Bean
	public NewTopic newTopic() {
		return new NewTopic("mainTopic",1, (short)1);
	}
}
