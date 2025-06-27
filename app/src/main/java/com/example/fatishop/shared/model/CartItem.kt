package com.example.fatishop.shared.model

data class CartItem(val productId: String = "",
                    val name: String = "",
                    val price: Double = 0.0,
                    val imageUrl: String = "",
                    val quantity: Int = 1,
                    val timestamp: Long = 0)
