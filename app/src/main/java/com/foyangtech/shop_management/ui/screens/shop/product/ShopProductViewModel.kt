package com.foyangtech.shop_management.ui.screens.shop.product

import androidx.compose.runtime.mutableStateOf
import com.foyangtech.shop_management.model.Product
import com.foyangtech.shop_management.model.Shop
import com.foyangtech.shop_management.model.service.LogService
import com.foyangtech.shop_management.model.service.StorageService
import com.foyangtech.shop_management.ui.screens.ShopManagementViewModel
import com.foyangtech.shop_management.util.UPDATE_PRODUCT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopProductViewModel@Inject constructor(
    logService: LogService,
    private val storageService: StorageService
) : ShopManagementViewModel(logService) {

    val shop = mutableStateOf(Shop())

    val currentToDeleteProduct = mutableStateOf(Product())

    fun initialize(shopId: String) {
        launchCatching(true) {
            launch(Dispatchers.IO){
                shop.value = storageService.getShop(shopId) ?: Shop()
            }
        }
    }

    fun getProducts(shopId: String) = storageService.getShopProducts(shopId)

    fun saveProduct(product: Product) {
        launchCatching {
            storageService.saveProductToShop(shop.value.id, product)
        }
    }

    fun onUpdateMenuClick(productId: String, openScreen: (String) -> Unit) =
        openScreen("$UPDATE_PRODUCT/${shop.value.id}/$productId")

    fun confirmDelete(productId: String) {
        launchCatching {
            storageService.deleteProductFromShop(shop.value.id, productId)
        }
    }

    fun onDeleteMenuClick(product: Product) {
        currentToDeleteProduct.value = product
    }
}