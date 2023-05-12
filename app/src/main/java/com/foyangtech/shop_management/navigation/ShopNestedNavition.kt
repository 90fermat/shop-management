package com.foyangtech.shop_management.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.foyangtech.shop_management.util.SHOP_HOME
import com.foyangtech.shop_management.util.SHOP_PRODUCT
import com.foyangtech.shop_management.util.SHOP_RECEIPT
import com.foyangtech.shop_management.util.SHOP_SCREEN
import com.foyangtech.shop_management.ui.screens.shop.home.ShopHomeScreen
import com.foyangtech.shop_management.ui.screens.shop.product.ShopProductScreen
import com.foyangtech.shop_management.ui.screens.shop.receipt.ShopReceiptScreen


fun NavGraphBuilder.shopGraph(navController: NavController) {
    navigation(startDestination = "$SHOP_HOME/{shopId}", route = "$SHOP_SCREEN/{shopId}") {
        val arguments = listOf(navArgument("shopId") { type = NavType.StringType })

        composable("$SHOP_HOME/{shopId}", arguments = arguments) {
            it.arguments?.getString("shopId")?.let { it1 -> ShopHomeScreen(shopId = it1) }
        }

        composable("$SHOP_PRODUCT/{shopId}", arguments = arguments) {
            it.arguments?.getString("shopId")?.let { it1 -> ShopProductScreen(shopId = it1) }
        }

        composable("$SHOP_RECEIPT/{shopId}", arguments = arguments) {
            it.arguments?.getString("shopId")?.let { it1 -> ShopReceiptScreen(shopId = it1) }
        }
    }
}