package com.example.fatishop.shared.model



data class Product(
    val id: String = "",
    val name: String = "",
    val brand: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val rating: Float = 0f,
    val inStock: Int = 0,
    val isPopular: Boolean = false,
    val description: String = ""
)
