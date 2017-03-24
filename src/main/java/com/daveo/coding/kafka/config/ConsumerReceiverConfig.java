/*
 * Copyright 2017 the original author Mohamed Babchia.
 * 
 * <p>Inspired from http://docs.spring.io/spring-kafka/docs/1.1.2.RELEASE
 * 
 */
package com.daveo.coding.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.daveo.coding.kafka.ConsumerReceiver;

/**
 *  Configuration class for kafka.consumer 
 *  properties 
 * @author Mohamed
 *
 */
@Configuration
@EnableKafka
public class ConsumerReceiverConfig {

	/**
	 * declaration of kafka.bootstrap-servers to connect to kafka 
	 */
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	/**
	 *  Declaration & initialization of consumerConfigs 
	 *  
	 */
	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		// set key deserializer type
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
		// set Value deserializer type
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);

		// set the consumer group if exist 
		// props.put(ConsumerConfig.GROUP_ID_CONFIG, "customerGroup");

		// allowing a logical application name to be included in server-side request logging
		//props.put(ConsumerConfig.CLIENT_ID_CONFIG, "customerServiceApi");

		return props;
	}

	/**
	 *  Declaration & initialization of ConsumerFactory 
	 *  
	 */
	@Bean
	public ConsumerFactory<Integer, String> factory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	/**
	 *  Declaration & initialization of listenerContainerFactory
	 *  used by @KafkaListener
	 *  
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<Integer, String> listenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(factory());

		return factory;
	}

	/**
	 *  Declaration & initialization of receiver
	 *  
	 *  
	 */
	@Bean
	public ConsumerReceiver receiver() {
		return new ConsumerReceiver();
	}

}
