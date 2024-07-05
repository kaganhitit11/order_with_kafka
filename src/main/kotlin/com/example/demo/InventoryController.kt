package com.example.demo

import org.apache.juli.logging.Log
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class InventoryController(private val kafkaProducer: KafkaProducer, private val inventoryService: InventoryService) {

    @GetMapping("/order")
    fun sendOrder(@RequestParam("message") id: String) {
        kafkaProducer.publishOrder(id)
    }

    @PostMapping("/addInventory")
    fun addInventory(@RequestParam product: Long, @RequestParam count: Int): Inventory {
        return inventoryService.addInventory(product, count)
    }

    @GetMapping("/allInventories")
    fun getAllInventories(): List<Inventory> {
        return inventoryService.findAllInventories()
    }

    @GetMapping("/check-db")
    fun checkDatabaseConnection(): List<Inventory> {
        return inventoryService.checkDatabaseConnection()
    }


}
