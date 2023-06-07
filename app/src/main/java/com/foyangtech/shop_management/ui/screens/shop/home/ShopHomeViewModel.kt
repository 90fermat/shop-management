package com.foyangtech.shop_management.ui.screens.shop.home

import androidx.compose.runtime.mutableStateOf
import com.foyangtech.shop_management.model.Product
import com.foyangtech.shop_management.model.Shop
import com.foyangtech.shop_management.model.service.LogService
import com.foyangtech.shop_management.model.service.StorageService
import com.foyangtech.shop_management.ui.screens.ShopManagementViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class ShopHomeViewModel@Inject constructor(
    logService: LogService,
    private val storageService: StorageService
) : ShopManagementViewModel(logService) {

    val shop = mutableStateOf(Shop())
    val products = mutableStateOf(emptyFlow<List<Product>>())

    fun initialize(shopId: String) {
        launchCatching {
            shop.value = storageService.getShop(shopId) ?: Shop()
        }
        launchCatching {
            products.value = storageService.getShopProducts(shopId)
        }
    }
}