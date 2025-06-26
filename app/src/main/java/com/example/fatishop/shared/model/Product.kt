package com.example.fatishop.shared.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName


data class Product(
val id: String = "",
val name: String = "",
val brand: String = "",
val price: Double = 0.0,
val imageUrl: String = "",
val rating: Float = 0f,
val inStock: Int = 0,
//val isPopular: Boolean = false,
//val description: String = "",
//val type: String = "",


@PropertyName("popular")
val isPopular: Boolean = false,

val description: String = "",
val type: String = "",

    // This field won't be stored in Firestore
@Exclude
val isFavorite: Boolean = false
) {
    // Empty constructor required for Firestore
    constructor() : this("", "", "", 0.0, "", 0f, 0, false, "", "", false)
}



