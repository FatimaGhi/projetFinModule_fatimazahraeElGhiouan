package com.example.fatishop.shared.model

data class User(
    val id: String = "",
    val email: String = "",
    val fullName: String = "",
    val profileImageUrl: String = "",
    val role: String = "customer"
)