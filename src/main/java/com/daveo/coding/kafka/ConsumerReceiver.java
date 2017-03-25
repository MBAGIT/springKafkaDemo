/*
 * Copyright 2017 the original author Mohamed Babchia.
 * 
 * <p>Inspired from http://docs.spring.io/spring-kafka/docs/1.1.2.RELEASE
 * 
 */
package com.daveo.coding.kafka;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
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
public class ConsumerReceiver extends KafkaConsumer<Integer, String> {

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
	@KafkaListener(topics = "springKafkaDemoTopic.t")
	public  void onReceiveMessage() {
		synchronized (this) {
			ConsumerRecords<Integer, String> records = this.poll(100);
			for (ConsumerRecord<Integer, String> record : records) {
				log.info("consuming from topic = {}, partition = {}, offset = {}, key = {}, value = {}", record.topic(),
						record.partition(), record.offset(), record.key(), record.value());
			}
		}

	}

	public ConsumerReceiver(Map<String, Object> configs) {
		super(configs);

	}

	@Override
	public ConsumerRecords<Integer, String> poll(long timeout) {
		// TODO Auto-generated method stub
		return super.poll(timeout);
	}
}
