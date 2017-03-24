/*
 * Copyright 2017 the original author Mohamed Babchia.
 * 
 * <p>Inspired from codenotfound spring-kafka-consumer-producer-example
 * 
 */
package com.daveo.coding.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.daveo.coding.kafka.ProducerSender;

/**
 * Configuration class for kafka.producer 
 *  properties 
 * 
 * @author Mohamed Babchia
 *
 */
@Configuration
public class ProducerSenderConfig {

	/**
	 * declaration of kafka.bootstrap-servers to connect to kafka 
	 */
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	/**
	 *  Declaration & initialization of producerConfiguration 
	 *  
	 */
	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		// key serialize type
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		// Value serialize type
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		// after 5000 message which it will throw a TimeoutException
		props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);

		return props;
	}

	/**
	 * Declaration & initialization  producerFactory
	 * 
	 */
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	/**
	 * declaration & Initialization   kafkaTemplate
	 * 
	 */
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<String, String>(producerFactory());
	}

	/**
	 * declaration & Initialization Sender	
	 * 
	 */
	@Bean
	public ProducerSender sender() {
		return new ProducerSender();
	}

}