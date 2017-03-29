/*
 * Copyright 2017 the original author Mohamed Babchia.
 * 
 * <p>Inspired from http://docs.spring.io/spring-kafka/docs/1.1.2.RELEASE
 * 
 */
package com.bmh.coding.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.bmh.coding.kafka.ConsumerReceiver;

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
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		// set Value deserializer type
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");

		// set the consumer group if exist 
		 props.put(ConsumerConfig.GROUP_ID_CONFIG, "customerGroupDemoUnit");

		// allowing a logical application name to be included in server-side request logging
		//props.put(ConsumerConfig.CLIENT_ID_CONFIG, "customerServiceApi");

		return props;
	}

	/**
	 *  Declaration & initialization of ConsumerFactory 
	 *  
	 */
	@Bean
	public ConsumerFactory<Integer, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	/**
	 *  Declaration & initialization of listenerContainerFactory
	 *  used by @KafkaListener
	 *  
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConcurrency(1);
		factory.setConsumerFactory(consumerFactory());

		return factory;
	}

	/**
	 *  Declaration & initialization of receiver
	 *  
	 *  
	 */
	@Bean
	public ConsumerReceiver receiver() {
		return new ConsumerReceiver(consumerConfigs());
	}

}
