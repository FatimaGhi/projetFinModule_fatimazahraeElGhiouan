package com.example.fatishop.shared.utils.firestore


import android.util.Log
import com.example.fatishop.shared.model.Product
import com.google.firebase.firestore.FirebaseFirestore

fun uploadProductsToFirestore(products: List<Product>) {
    val db = FirebaseFirestore.getInstance()
    val collection = db.collection("products")

    products.forEach { product ->
        collection.document(product.id)
            .set(product)
            .addOnSuccessListener {
                Log.d("Upload", " Added ${product.name}")
            }
            .addOnFailureListener {
                Log.e("Upload", " Failed ${product.name}: ${it.message}")
            }
    }
}
