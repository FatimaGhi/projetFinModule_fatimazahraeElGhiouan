package com.example.fatishop.features.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fatishop.shared.model.Product
import com.example.fatishop.shared.repository.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _brands = MutableStateFlow<List<String>>(emptyList())
    val brands: StateFlow<List<String>> = _brands.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    init {
        loadProducts()
        loadBrands()
    }

    private fun loadProducts(brand: String = "") {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _products.value = if (brand.isEmpty()) {
                    productRepository.getAllProducts()
                } else {
                    productRepository.getProductsByBrand(brand)
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadBrands() {
        viewModelScope.launch {
            try {
                _brands.value = productRepository.getAllBrands()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun filterByBrand(brand: String) {
        loadProducts(brand)
    }

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }

    fun clearSelectedProduct() {
        _selectedProduct.value = null
    }

    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            productRepository.toggleFavorite(productId)
            // Optionally refresh the products list after toggling
            loadProducts()
        }
    }
    fun addToCart(
        userId: String,
        product: Product,
        quantity: Int,
        isLoading: MutableState<Boolean>,
        onSuccess: () -> Unit
    ) {
        isLoading.value = true

        val cartItem = hashMapOf(
            "productId" to product.id,
            "name" to product.name,
            "price" to product.price,
            "imageUrl" to product.imageUrl,
            "quantity" to quantity,
            "timestamp" to System.currentTimeMillis()
        )

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("cart")
            .document(product.id)
            .set(cartItem)
            .addOnSuccessListener {
                Log.d("Cart", "✅ Added to cart")
            }
            .addOnFailureListener {
                Log.e("Cart", "❌ Failed to add to cart: ${it.message}")
            }
    }

}