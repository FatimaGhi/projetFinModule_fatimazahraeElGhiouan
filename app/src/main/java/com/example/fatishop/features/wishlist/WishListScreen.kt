package com.example.fatishop.features.wishlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.fatishop.features.components.ProductCard
import com.example.fatishop.features.home.HomeViewModel
import com.example.fatishop.shared.local.DataStoreManager
import com.example.fatishop.ui.theme.BackgroundColor
import com.example.fatishop.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val userId by dataStoreManager.userId.collectAsState(initial = "")

    val products by viewModel.products.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    // Load favorites when userId is ready
    LaunchedEffect(userId) {
        if (userId.isNotBlank()) {
            viewModel.loadFavoriteProducts(userId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My Favorites", fontSize = 20.sp, color = PrimaryColor)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = BackgroundColor
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when {
                userId.isBlank() -> {
                    Text(
                        text = "Please log in to view favorites.",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                products.isEmpty() -> {
                    Text(
                        text = "No favorites yet!",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(products) { product ->
                            ProductCard(
                                product = product,
                                onItemClick = {
                                    viewModel.selectProduct(product)
                                },
                                onFavoriteClick = {
                                    viewModel.toggleFavorite(userId, product.id)
                                    viewModel.loadFavoriteProducts(userId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
