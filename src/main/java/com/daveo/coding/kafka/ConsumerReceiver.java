/*
 * Copyright 2017 the original author Mohamed Babchia.
 * 
 * <p>Inspired from http://docs.spring.io/spring-kafka/docs/1.1.2.RELEASE
 * 
 */
package com.daveo.coding.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * 
 * Receiver message class for Consumer 
 * created with Spring Kafka listener .
 * 
 * 
 * @author Mohamed
 *
 */
public class ConsumerReceiver {
	
	/**
	 * Logger for this class
	 */
	private static final Logger log = LoggerFactory.getLogger(ConsumerReceiver.class);

	
	
	
	/**
	 * implementation receiveMessage
	 * wish use @KafkaListener with "springKafkaDemoTopic.t" as topic 
	 * 
	 * @param message
	 *           message to receive
	 */
	@KafkaListener(topics="springKafkaDemoTopic.t")
	public void receiveMessage(String message) {
		log.info("received message='{}'", message);
				
	}
}
