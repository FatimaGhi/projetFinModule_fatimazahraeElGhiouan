package com.example.fatishop.shared.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.fatishop.shared.utils.Routes

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: String
) {
    object Home : BottomNavItem(Routes.HOME, Icons.Default.Home, "Home")
    object Cart : BottomNavItem(Routes.CART, Icons.Default.ShoppingCart, "Cart")
    object Wishlist : BottomNavItem(Routes.WISHLIST, Icons.Default.Favorite, "Wishlist")
    object Profile : BottomNavItem(Routes.PROFILE, Icons.Default.Person, "Profile")
}
