package com.foyangtech.shop_management.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.foyangtech.shop_management.RootAppState
import com.foyangtech.shop_management.ui.screens.shop.home.ShopHomeScreen
import com.foyangtech.shop_management.ui.screens.shop.product.ShopProductScreen
import com.foyangtech.shop_management.ui.screens.shop.product.edit.EditProductScreen
import com.foyangtech.shop_management.ui.screens.shop.receipt.ShopReceiptScreen
import com.foyangtech.shop_management.util.SHOP_HOME
import com.foyangtech.shop_management.util.SHOP_PRODUCT
import com.foyangtech.shop_management.util.SHOP_RECEIPT
import com.foyangtech.shop_management.util.SHOP_SCREEN
import com.foyangtech.shop_management.util.UPDATE_PRODUCT
import com.foyangtech.shop_management.util.UPDATE_SHOP


fun NavGraphBuilder.shopGraph(appState: RootAppState) {

    navigation(startDestination = "$SHOP_HOME/{shopId}", route = "$SHOP_SCREEN/{shopId}") {

        val shopIdArgument = navArgument("shopId") { type = NavType.StringType }
        val productIdArgument = navArgument("productId") { type = NavType.StringType }
        val arguments = listOf(shopIdArgument)

        composable("$SHOP_HOME/{shopId}", arguments = arguments) {
            it.arguments?.getString("shopId")?.let { it1 -> ShopHomeScreen(shopId = it1) }
        }

        composable("$SHOP_PRODUCT/{shopId}", arguments = arguments) {
            it.arguments?.getString("shopId")?.let { it1 ->
                ShopProductScreen(
                    shopId = it1,
                    openScreen = { route -> appState.navigate(route) }
                )
            }
        }

        composable("$SHOP_RECEIPT/{shopId}", arguments = arguments) {
            it.arguments?.getString("shopId")?.let { it1 -> ShopReceiptScreen(shopId = it1) }
        }

       /* composable("$UPDATE_SHOP/{shopId}",
            arguments = arguments) {
            it.arguments?.getString("shopId")?.let { shopId ->
                EditShopScreen(
                    shopId = shopId,
                    popScreen = { appState.popUp() }
                )
            }
        }*/

        composable(
            route = "$UPDATE_PRODUCT/{shopId}/{productId}",
            arguments = listOf(shopIdArgument, productIdArgument)
        ) {
            it.arguments?.getString("shopId")?.let { shopId ->
                it.arguments?.getString("productId")?.let { productId ->
                    EditProductScreen(
                        shopId = shopId,
                        productId = productId,
                        popScreen = { appState.popUp() }
                    )
                }
            }
        }
    }
}