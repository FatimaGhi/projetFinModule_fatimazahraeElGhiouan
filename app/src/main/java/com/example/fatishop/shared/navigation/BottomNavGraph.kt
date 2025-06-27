package com.example.fatishop.shared.navigation


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fatishop.features.cart.CartScreen
import com.example.fatishop.features.cart.CartViewModel
import com.example.fatishop.features.home.HomeScreen
//import com.example.fatishop.features.home.HomeScreen
import com.example.fatishop.features.profile.ProfileScreen
import com.example.fatishop.features.wishlist.WishListScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(BottomNavItem.Cart.route) {
            val viewModel: CartViewModel = hiltViewModel()
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

            if (userId.isNotEmpty()) {
                CartScreen(
                    viewModel = viewModel,
                    userId = userId
                )
            } else {
                Text("Please login first")
            }
        }
        composable(BottomNavItem.Wishlist.route) { WishListScreen() }
        composable(BottomNavItem.Profile.route) { ProfileScreen() }
    }
}


