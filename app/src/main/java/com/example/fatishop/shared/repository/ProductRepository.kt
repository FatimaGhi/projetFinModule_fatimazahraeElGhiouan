package com.example.fatishop.shared.repository

import androidx.compose.runtime.MutableState
import com.example.fatishop.shared.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
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

    suspend fun getAllBrands(): List<String> {
        return productsCollection
            .get()
            .await()
            .toObjects(Product::class.java)
            .map { it.brand }
            .distinct()
            .sorted()
    }

    suspend fun getProductsByBrand(brand: String): List<Product> {
        return try {
            productsCollection
                .whereEqualTo("brand", brand)
                .get()
                .await()
                .toObjects(Product::class.java)
        } catch (e: Exception) {
            println("Error getting products by brand: ${e.message}")
            emptyList()
        }
    }

     suspend fun toggleFavorite(productId: String) {
        val productRef = firestore.collection("products").document(productId)
        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(productRef)
            val currentFavorite = snapshot.getBoolean("isFavorite") ?: false
            transaction.update(productRef, "isFavorite", !currentFavorite)
        }.await()
    }

    suspend fun addToCart(
        userId: String,
        productId: String,
        name: String,
        price: Double,
        imageUrl: String,
        quantity: Int
    ) {
        val cartItem = hashMapOf(
            "productId" to productId,
            "name" to name,
            "price" to price,
            "imageUrl" to imageUrl,
            "quantity" to quantity,
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("users")
            .document(userId)
            .collection("cart")
            .document(productId)
            .set(cartItem)
            .await()
    }



}
