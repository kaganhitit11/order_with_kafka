package com.example.demo

import org.springframework.stereotype.Service

@Service
class InventoryService(private val inventoryRepository: InventoryRepository) {

    fun addInventory(productId: Long, count: Int): Inventory {
        val inventory = Inventory(id=4, product = productId, count = count)
        return inventoryRepository.save(inventory)
    }

    fun findAllInventories(): List<Inventory> {
        return inventoryRepository.findAll()
    }

    fun checkDatabaseConnection(): List<Inventory> {
        return inventoryRepository.findAll()
    }
}
