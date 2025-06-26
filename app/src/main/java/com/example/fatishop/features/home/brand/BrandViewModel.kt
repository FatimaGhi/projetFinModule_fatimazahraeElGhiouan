//package com.example.fatishop.features.home.brand
//
//
//
//import android.util.Log
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import com.example.fatishop.shared.model.Product
//import com.example.fatishop.shared.repository.ProductRepository
//import androidx.compose.runtime.State
//
//
//class BrandViewModel : ViewModel() {
//
//    private val repository = ProductRepository()
//    private val _productsByBrand = mutableStateOf<List<Product>>(emptyList())
//    val productsByBrand: State<List<Product>> = _productsByBrand
//
//    fun loadProductsByBrand(brand: String) {
//        repository.getProductsByBrand(brand) { products ->
//            _productsByBrand.value = products
//        }
//    }
//}
