package com.example.fatishop.shared.repository

import com.example.fatishop.shared.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val productsCollection = firestore.collection("products")

    suspend fun addProduct(product: Product) {
        productsCollection.document(product.id).set(product).await()
    }

    suspend fun getAllProducts(): List<Product> {
        return productsCollection.get().await().toObjects(Product::class.java)
    }

    suspend fun getProductById(id: String): Product? {
        return productsCollection.document(id).get().await().toObject(Product::class.java)
    }

    suspend fun updateProduct(product: Product) {
        productsCollection.document(product.id).set(product).await()
    }

    suspend fun deleteProduct(id: String) {
        productsCollection.document(id).delete().await()
    }

    suspend fun getPopularProducts(): List<Product> {
        return productsCollection
            .whereEqualTo("popular", true)
            .get()
            .await()
            .toObjects(Product::class.java)
    }

    suspend fun getFeaturedProducts(): List<Product> {
        return productsCollection
            .whereEqualTo("popular", true)
            .limit(10)
            .get()
            .await()
            .toObjects(Product::class.java)
            .map { it.copy(isFavorite = false) }
    }
}