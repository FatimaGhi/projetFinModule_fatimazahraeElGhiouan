package com.example.fatishop.features.cart

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.fatishop.shared.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val firestore: FirebaseFirestore
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

}

