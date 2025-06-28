package com.example.fatishop.features.cart

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.fatishop.shared.model.CartItem
import com.example.fatishop.shared.model.Order
import com.example.fatishop.shared.repository.OrderRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


//@HiltViewModel
//class CartViewModel @Inject constructor(  private val firestore: FirebaseFirestore) : ViewModel() {
//
////    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
////    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()
//
//    private val _cartItems = mutableStateListOf<CartItem>()
//    val cartItems: List<CartItem> = _cartItems
//
////    fun loadCart(userId: String) {
////        FirebaseFirestore.getInstance()
////            .collection("users")
////            .document(userId)
////            .collection("cart")
////            .addSnapshotListener { snapshot, _ ->
////                if (snapshot != null) {
////                    _cartItems.value = snapshot.toObjects(CartItem::class.java)
////                }
////            }
////    }
//    fun loadCart(userId: String) {
//        firestore.collection("users")
//            .document(userId)
//            .collection("cart")
//            .addSnapshotListener { snapshot, _ ->
//                snapshot?.let {
//                    _cartItems.clear()
//                    for (doc in it.documents) {
//                        doc.toObject(CartItem::class.java)?.let { item ->
//                            _cartItems.add(item)
//                        }
//                    }
//                }
//            }
//    }
//
//}
//
//fun updateQuantity(userId: String, productId: String, quantity: Int) {
//    firestore.collection("users")
//        .document(userId)
//        .collection("cart")
//        .document(productId)
//        .update("quantity", quantity)
//
//}
@HiltViewModel
class CartViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems

    fun loadCart(userId: String) {
        firestore.collection("users")
            .document(userId)
            .collection("cart")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    _cartItems.clear()
                    for (doc in it.documents) {
                        doc.toObject(CartItem::class.java)?.let { item ->
                            _cartItems.add(item)
                        }
                    }
                }
            }
    }

    fun updateQuantity(userId: String, productId: String, quantity: Int) {
        firestore.collection("users")
            .document(userId)
            .collection("cart")
            .document(productId)
            .update("quantity", quantity)
    }
    fun increaseQuantity(userId: String, productId: String) {
        val currentItem = cartItems.find { it.productId == productId }
        currentItem?.let {
            updateQuantity(userId, productId, it.quantity + 1)
        }
    }

    fun decreaseQuantity(userId: String, productId: String) {
        val currentItem = cartItems.find { it.productId == productId }
        currentItem?.let {
            if (it.quantity > 1) {
                updateQuantity(userId, productId, it.quantity - 1)
            }
        }
    }

    fun checkout(userId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val orderItems = cartItems.map { it.copy() } // Clone items
        val orderData = hashMapOf(
            "items" to orderItems.map {
                mapOf(
                    "productId" to it.productId,
                    "name" to it.name,
                    "price" to it.price,
                    "quantity" to it.quantity
                )
            },
            "total" to orderItems.sumOf { it.price * it.quantity },
            "timestamp" to com.google.firebase.Timestamp.now()
        )

        firestore.collection("users")
            .document(userId)
            .collection("orders")
            .add(orderData)
            .addOnSuccessListener {
                // Clear cart
                clearCart(userId)
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    fun clearCart(userId: String) {
        firestore.collection("users")
            .document(userId)
            .collection("cart")
            .get()
            .addOnSuccessListener { snapshot ->
                for (doc in snapshot.documents) {
                    doc.reference.delete()
                }
            }
    }

    fun checkoutWithAddress(
        userId: String,
        name: String,
        address: String,
        phone: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val orderItems = cartItems.map { it.copy() }
        orderItems.forEach {
            Log.d("CheckoutDebug", "Item: ${it.name}, Price: ${it.price}, Quantity: ${it.quantity}")
        }
        val totalPrice = orderItems.sumOf { it.price * it.quantity }

        Log.d("CheckoutDebug", "Total price calculated: $totalPrice")

        val orderData = hashMapOf(
            "userId" to userId,
            "name" to name,
            "address" to address,
            "phone" to phone,
            "items" to orderItems.map {
                mapOf(
                    "productId" to it.productId,
                    "name" to it.name,
                    "price" to it.price,
                    "quantity" to it.quantity
                )
            },
            "total" to orderItems.sumOf { it.price * it.quantity },
            "status" to "Pending",
            "timestamp" to com.google.firebase.Timestamp.now()
        )
        Log.d("CHECKOUT_DEBUG", "Total price: ${orderItems.sumOf { it.price * it.quantity }}")

        firestore.collection("orders")
            .add(orderData)
            .addOnSuccessListener {
                clearCart(userId)
                onSuccess()
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun getOrdersForUser(userId: String): Flow<List<Order>> {
        return orderRepository.getOrdersForUser(userId)
    }

    fun deleteCartItem(userId: String, productId: String) {
        firestore.collection("users")
            .document(userId)
            .collection("cart")
            .document(productId)
            .delete()
    }



}

