package com.example.fatishop.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fatishop.shared.model.Product
import com.example.fatishop.shared.repository.ProductRepository
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
}