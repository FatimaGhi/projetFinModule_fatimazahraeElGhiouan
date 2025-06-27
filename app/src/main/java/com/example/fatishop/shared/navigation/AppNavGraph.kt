import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.fatishop.features.components.ProductDetailsScreen
import com.example.fatishop.features.home.HomeScreen
import com.example.fatishop.features.home.HomeViewModel
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
        composable(Routes.HOME) { backStackEntry ->

            val homeViewModel: HomeViewModel = hiltViewModel()

            HomeScreen(
                onProductClick = { productId ->

//                    navController.navigate("${Routes.PRODUCT_DETAILS}/$productId")
                },
                onBrandSelected = { brand ->

                    homeViewModel.filterByBrand(brand)
                }
            )
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
