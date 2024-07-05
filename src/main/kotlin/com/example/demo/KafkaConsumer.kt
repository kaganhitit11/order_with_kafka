package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.beans.factory.annotation.Autowired

@Service
class KafkaConsumer(@Autowired private val inventoryRepository: InventoryRepository,
                    @Autowired private val kafkaTemplate: KafkaTemplate<String, String>) {

    private val logger = LoggerFactory.getLogger(KafkaConsumer::class.java)

    @KafkaListener(topics = ["orders"], groupId = "group_id")
    fun validateOrder(id: String){
        logger.info("ID HERE IS AS FOLLOWS: {}", id)
        val inventory = inventoryRepository.findById(id.toLong())

        if (inventory.isPresent) {
            val count = inventory.get().count
            val topic = if (count > 0) "order-validated" else "order-rejected"
            kafkaTemplate.send(topic, id)
            logger.info("Order with $id validated and published to order-validated.")
        } else {
            kafkaTemplate.send("order-rejected", id)
            logger.info("Invalid order with $id")
        }
    }

    @KafkaListener(topics = ["order-validated"], groupId = "group_id")
    fun decreaseOrderCount(message: String) {
        val inventory = inventoryRepository.findById(message.toLong())

        // Check if the inventory exists
        if (inventory.isPresent) {
            val inventoryItem = inventory.get()
            // Decrease the count
            inventoryItem.count -= 1
            // Save the updated inventory item back to the repository
            inventoryRepository.save(inventoryItem)
            logger.info("Decreased count for id: ${inventoryItem.id}. New count: ${inventoryItem.count}")
        } else {
            logger.warn("Inventory item not found for id: $message")
        }
    }

    @KafkaListener(topics = ["order-rejected"], groupId = "group_id")
    fun LogInvalidOrder(message: String){
        logger.info("Order with id {} not validated. Out of stock.", message)
    }
}
