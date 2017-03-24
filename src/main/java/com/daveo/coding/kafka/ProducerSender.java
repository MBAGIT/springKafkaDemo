/*
 * Copyright 2017 the original author Mohamed Babchia.
 * 
 * <p>Inspired from http://docs.spring.io/spring-kafka/docs/1.1.2.RELEASE
 * 
 */
package com.daveo.coding.kafka;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * 
 * sender message class for Producer 
 * For sending messages we will be using the KafkaTemplate 
 * which wraps a Producer and provides convenience methods to send data to Kafka topics
 * 
 * @author Mohamed Babchia
 *
 */
public class ProducerSender {

	/*
	 * Logger for sender Class
	 */
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(ProducerSender.class);

	/*
	 * injection of kafkaTemplate 
	 */
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	/**
	 * Implementation of sendMessage  
	 * Which take topic and message as parameter and returning a Future
	 * with callBack method to implement and verify returned value.
	 * 
	 * @param topic
	 * @param message
	 */
	public void sendMessage(String topic, String message) {

		/**
		 *  init of futur with send method of kafkaTemplate
		 */
		ListenableFuture<SendResult<String, String>> futur = kafkaTemplate.send(topic, message);

		/**
		 * callback method for futur
		 */
		futur.addCallback(

				new ListenableFutureCallback<SendResult<String, String>>() {

					@Override
					public void onSuccess(SendResult<String, String> result) {
						log.info("success sent message='{}' with offset={}", message,
								result.getRecordMetadata().offset());

					}

					@Override
					public void onFailure(Throwable ex) {
						log.error("failure unable to send message='{}'", message, ex);

					}

				}

		);

		// alternatively, to block the sending  invoke the future's get() method

	}

}
