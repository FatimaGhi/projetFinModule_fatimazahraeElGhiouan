package com.example.fatishop.shared.model

import com.google.firebase.Timestamp

data class Order(
    val userId: String = "",
    val name: String = "",
    val address: String = "",
    val phone: String = "",
    val total: Double = 0.0,
    val status: String = "Pending",
    val timestamp: Timestamp? = null,
    val items: List<Map<String, Any>> = emptyList()
)
