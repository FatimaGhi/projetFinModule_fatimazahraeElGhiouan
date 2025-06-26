import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fatishop.features.auth.login.LoginScreen
import com.example.fatishop.features.auth.register.RegisterScreen
import com.example.fatishop.features.cart.CartScreen
//import com.example.fatishop.features.home.HomeScreen

import com.example.fatishop.features.main.MainScreen
import com.example.fatishop.features.profile.ProfileScreen
import com.example.fatishop.features.welcome.WelcomeScreen
import com.example.fatishop.features.wishlist.WishListScreen
import com.example.fatishop.shared.utils.Routes
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.fatishop.features.home.HomeScreen
import com.example.fatishop.features.home.brand.BrandScreen


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
//        composable(Routes.HOME) {
//            HomeScreen(navController)
//        }
        composable(Routes.HOME) {
            HomeScreen()
        }
//        composable("product/{productId}") { backStackEntry ->
//            val productId = backStackEntry.arguments?.getString("productId") ?: ""
//            ProductDetailScreen(productId = productId)
//        }



        composable(Routes.CART) {
            CartScreen()
        }

        composable(Routes.WISHLIST) {
            WishListScreen()
        }

        composable(Routes.PROFILE) {
            ProfileScreen()
        }
        composable(Routes.MAIN) {
            MainScreen()
        }

//        composable(
//            route = Routes.BRAND,
//            arguments = listOf(navArgument("brandName") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val brand = backStackEntry.arguments?.getString("brandName")
//            if (!brand.isNullOrEmpty()) {
//                BrandScreen(
//                    brand = brand,
//                    navController = navController
//                )
//            }
//        }








    }
}
