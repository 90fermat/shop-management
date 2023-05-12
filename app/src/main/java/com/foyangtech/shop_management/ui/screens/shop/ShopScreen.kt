package com.foyangtech.shop_management.ui.screens.shop

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.ui.graphics.vector.ImageVector
import com.foyangtech.shop_management.R
import com.foyangtech.shop_management.util.SHOP_HOME
import com.foyangtech.shop_management.util.SHOP_PRODUCT
import com.foyangtech.shop_management.util.SHOP_RECEIPT

sealed class ShopScreen(val route: String,
                        @StringRes val resourceId: Int,
                        val icon: ImageVector) {
    object Home : ShopScreen(SHOP_HOME, R.string.navigation_bar_home, Icons.Filled.Home)
    object Products : ShopScreen(SHOP_PRODUCT, R.string.navigation_bar_products, Icons.Filled.ViewList)
    object Recipe : ShopScreen(SHOP_RECEIPT, R.string.navigation_bar_recipe, Icons.Filled.Receipt)
}

val shopScreens = listOf<ShopScreen>(ShopScreen.Home, ShopScreen.Products, ShopScreen.Recipe)


