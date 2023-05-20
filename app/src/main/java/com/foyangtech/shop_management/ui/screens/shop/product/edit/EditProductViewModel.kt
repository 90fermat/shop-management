package com.foyangtech.shop_management.ui.screens.shop.product.edit

import androidx.compose.runtime.mutableStateOf
import com.foyangtech.shop_management.model.Product
import com.foyangtech.shop_management.model.service.LogService
import com.foyangtech.shop_management.model.service.StorageService
import com.foyangtech.shop_management.ui.screens.ShopManagementViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel@Inject constructor(
   logService: LogService,
   private val storageService: StorageService
) : ShopManagementViewModel(logService) {

   val product = mutableStateOf(Product())

   val uiState = mutableStateOf(EditProductUiState())

   fun initialize(shopId: String, productId: String) {
      launchCatching(true) {
         product.value = storageService.getProductFromShop(shopId, productId) ?: Product()
         stateChange(product.value .name, "${product.value.price}",
            "${product.value.shopPrice}", "${product.value.stockInShop}",
            product.value.unit)
      }
   }
   fun onNameChange(newValue: String) {
      uiState.value = uiState.value.copy(name = newValue)
   }

   fun onPriceChange(newValue: String) {
      uiState.value = uiState.value.copy(price = newValue)
   }

   fun onShopPriceChange(newValue: String) {
      uiState.value = uiState.value.copy(shopPrice = newValue)
   }

   fun onStockChange(newValue: String) {
      uiState.value = uiState.value.copy(stock = newValue)
   }

   fun onUnitChange(newValue: String) {
      uiState.value = uiState.value.copy(unit = newValue)
   }

   fun updateState(name: String, price: String, shopPrice: String, stock: String, unit: String) {
      uiState.value = uiState.value.copy(name, price, shopPrice, stock, unit)
      product.value = product.value.copy(
         name = name,
         price = price.toDouble(),
         shopPrice = shopPrice.toDouble(),
         stockInShop = stock.toDouble(),
         unit = unit
      )
   }
   private fun stateChange(
      name: String, price: String, shopPrice: String,
      stock: String, unit: String) {
      uiState.value = uiState.value.copy(name, price, shopPrice, stock, unit)
   }

   fun updateProduct(shopId: String, popScreen: () -> Unit) {
      launchCatching {
         if (product.value.id.isBlank())
            storageService.saveProductToShop(shopId, product.value)
         else
            storageService.updateProductFromShop(shopId, product.value)
      }
      popScreen()
   }

}