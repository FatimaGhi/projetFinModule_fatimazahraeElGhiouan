//import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fatishop.features.auth.login.LoginScreen
import com.example.fatishop.features.auth.register.RegisterScreen
import com.example.fatishop.features.cart.CartScreen

import com.example.fatishop.features.main.MainScreen
//import com.example.fatishop.features.profile.ProfileScreen
import com.example.fatishop.features.welcome.WelcomeScreen
//import com.example.fatishop.features.wishlist.WishListScreen
import com.example.fatishop.shared.utils.Routes
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fatishop.features.Checkout.CheckoutFormScreen
import com.example.fatishop.features.cart.CartViewModel
import com.example.fatishop.features.components.ProductDetailsScreen
import com.example.fatishop.features.home.HomeScreen
import com.example.fatishop.features.wishlist.FavoritesScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.WELCOME) {

        composable(Routes.WELCOME) {
            WelcomeScreen(navController)
        }

        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }

        composable(Routes.REGISTER) {
            RegisterScreen(navController)
        }

        composable(Routes.HOME) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Routes.PRODUCT_DETAILS,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailsScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() }
            )
        }


        composable(Routes.CART) {
            val viewModel: CartViewModel = hiltViewModel()
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

            if (userId.isNotEmpty()) {
                CartScreen(
                    navController = navController,
                    viewModel = viewModel,
//                    userId = userId
                )
            } else {
                Text("User not logged in")
            }
        }

        composable(Routes.WISHLIST) {
            FavoritesScreen( navController = navController)
        }

        composable(Routes.PROFILE) {
            ProfileScreen()
        }
        composable(Routes.MAIN) {
            MainScreen()
        }
        composable("checkout_form") {
            CheckoutFormScreen(
                navController = navController,
                onOrderSuccess = {
                    navController.navigate(Routes.HOME)
                }
            )
        }
    }
}
