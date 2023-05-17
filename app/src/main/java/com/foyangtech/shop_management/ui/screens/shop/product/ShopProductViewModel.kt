package com.foyangtech.shop_management.ui.screens.shop.product

import androidx.compose.runtime.mutableStateOf
import com.foyangtech.shop_management.model.Product
import com.foyangtech.shop_management.model.Shop
import com.foyangtech.shop_management.model.service.LogService
import com.foyangtech.shop_management.model.service.StorageService
import com.foyangtech.shop_management.ui.screens.ShopManagementViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopProductViewModel@Inject constructor(
    logService: LogService,
    private val storageService: StorageService
) : ShopManagementViewModel(logService) {

    val shop = mutableStateOf(Shop())
    var uiState = mutableStateOf(ShopProductUiState())
        private set
    private val search
        get() = uiState.value.searchText

    var name = mutableStateOf(uiState.value.name)
    var price = mutableStateOf(uiState.value.price)
    var shopPrice = mutableStateOf(uiState.value.shopPrice)
    var stock = mutableStateOf(uiState.value.stock)
    var unit = mutableStateOf(uiState.value.unit)

    /*private val name
        get() = uiState.value.name
    private val price
        get() = uiState.value.price
    private val shopPrice
        get() = uiState.value.shopPrice
    private val stock
        get() = uiState.value.stock
    private val unit
        get() = uiState.value.unit*/

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

    fun updateProduct(product: Product, shopId: String) {
        launchCatching {
            storageService.updateProductFromShop(shopId,
                product.copy(
                    name = name.value, price = uiState.value.price,
                    shopPrice = uiState.value.shopPrice, stockInShop = uiState.value.stock,
                    unit = uiState.value.unit
                )
            )
        }
    }

}