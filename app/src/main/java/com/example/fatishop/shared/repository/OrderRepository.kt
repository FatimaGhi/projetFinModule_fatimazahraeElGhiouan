package com.example.fatishop.shared.repository


import com.example.fatishop.shared.model.Order
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class OrderRepository(
    private val firestore: FirebaseFirestore
) {
    fun getOrdersForUser(userId: String): Flow<List<Order>> = callbackFlow {
        val listener = firestore.collection("orders")
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val orders = snapshot?.toObjects(Order::class.java) ?: emptyList()
                trySend(orders)
            }

        awaitClose { listener.remove() }
    }
}
