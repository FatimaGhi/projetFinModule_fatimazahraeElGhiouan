package com.example.fatishop.features.home.brand

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.fatishop.shared.model.Product
import com.example.fatishop.features.components.ProductCard

@Composable
fun BrandScreen(brandName: String, navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Showing products for brand: $brandName",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sample products for the brand
        val sampleProducts = listOf(
            "$brandName Air Max",
            "$brandName Classic",
            "$brandName Pro",
            "$brandName Sport"
        )

        Text(
            "Products:",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        sampleProducts.forEach { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = product,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Back button
        navController?.let {
            Button(
                onClick = { it.popBackStack() }
            ) {
                Text("Go Back")
            }
        }
    }
}



