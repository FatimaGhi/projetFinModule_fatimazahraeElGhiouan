package com.example.fatishop.shared.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fatishop.features.cart.CartScreen
import com.example.fatishop.features.home.HomeScreen
//import com.example.fatishop.features.home.HomeScreen
import com.example.fatishop.features.profile.ProfileScreen
import com.example.fatishop.features.wishlist.WishListScreen

@Composable
fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) { HomeScreen(onProductClick = { productId ->
            navController.navigate("product_details/$productId")
        },
            onBrandSelected = { brand ->

            }) }
        composable(BottomNavItem.Cart.route) { CartScreen() }
        composable(BottomNavItem.Wishlist.route) { WishListScreen() }
        composable(BottomNavItem.Profile.route) { ProfileScreen() }
    }
}


