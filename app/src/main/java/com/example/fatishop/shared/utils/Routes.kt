package com.example.fatishop.shared.utils

import android.net.Uri

object Routes {

    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val CART = "cart"
    const val WISHLIST = "wishlist"
    const val PROFILE = "profile"
    const val MAIN = "main"
    const val BRAND = "brand/{brandName}"

    fun brandRoute(brandName: String): String {
        val encodedBrand = Uri.encode(brandName)
        return "brand/$encodedBrand"
    }

    const val PRODUCT_DETAILS = "product_details/{productId}"

    fun productDetailsRoute(productId: String): String =
        "product_details/$productId"

}
