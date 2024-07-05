package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducer(private val kafkaTemplate: KafkaTemplate<String, String>) {

    private val logger = LoggerFactory.getLogger(KafkaProducer::class.java)
    private val orders = "orders"

    fun publishOrder(message: String){
        logger.info("Order with id $message received and directing to inventory for validation.")
        kafkaTemplate.send(orders, message)
    }

}
