package com.example.demo

import jakarta.persistence.*

@Entity
data class Inventory(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        //@Column(name = "product_id", nullable = true)
        val product: Long,
        var count: Int
)