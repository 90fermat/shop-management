package com.foyangtech.shop_management.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import com.foyangtech.shop_management.model.Shop
import com.foyangtech.shop_management.model.service.LogService
import com.foyangtech.shop_management.model.service.StorageService
import com.foyangtech.shop_management.ui.screens.ShopManagementViewModel
import com.foyangtech.shop_management.util.SETTINGS_SCREEN
import com.foyangtech.shop_management.util.SHOP_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    logService: LogService,
    private val storageService: StorageService
) : ShopManagementViewModel(logService) {


    var uiState = mutableStateOf(HomeUiState())
        private set

    private val showDialogShopName
        get() = uiState.value.showDialogShopName
    private val showDialogShopDescription
        get() = uiState.value.showDialogShopDescription
    private val showDialogShopCurrency
        get() = uiState.value.showDialogShopCurrency
    private val updatedShopCurrency
        get() = uiState.value.updatedShopCurrency
    private val updatedShopName
        get() = uiState.value.updatedShopName
    private val updatedShopDescription
        get() = uiState.value.updatedShopDescription

    val currentSelectedShop = mutableStateOf(Shop())

    val shops = storageService.shops

    fun onShopNameChange(newValue: String) {
        uiState.value = uiState.value.copy(showDialogShopName = newValue)
    }
    fun onUpdateShopNameChange(newValue: String) {
        uiState.value = uiState.value.copy(updatedShopName = newValue)
    }

    fun onShopDescriptionChange(newValue: String) {
        uiState.value = uiState.value.copy(showDialogShopDescription = newValue)
    }

    fun onUpdateShopDescriptionChange(newValue: String) {
        uiState.value = uiState.value.copy(updatedShopDescription = newValue)
    }

    fun onShopCurrencyChange(newValue: String) {
        uiState.value = uiState.value.copy(showDialogShopCurrency = newValue)
    }

    fun onUpdateShopCurrencyChange(newValue: String) {
        uiState.value = uiState.value.copy(updatedShopCurrency = newValue)
    }

    fun addShop() {
        launchCatching {
            storageService.saveShop(
                Shop("",
                    showDialogShopName.trim(),
                    showDialogShopDescription.trim(),
                    showDialogShopCurrency
                )
            )
        }

        uiState.value.showDialogShopName = ""
        uiState.value.showDialogShopDescription = ""
        uiState.value.showDialogShopCurrency = ""
    }

    fun updateShop(shop: Shop) {
        launchCatching {
            storageService.updateShop(
                shop.copy(name= updatedShopName.trim(),
                description = updatedShopDescription.trim(),
                currency = updatedShopCurrency))
        }
    }

    fun deleteShop(shopId: String) {
        launchCatching {
            storageService.deleteShop(shopId)
        }
    }

    fun onUpdateMenuClick(shop: Shop) {
        currentSelectedShop.value = shop
        onUpdateShopNameChange(shop.name)
        onUpdateShopDescriptionChange(shop.description)
        onUpdateShopCurrencyChange(shop.currency)
    }

    fun onDeleteMenuClick(shop: Shop) {
        currentSelectedShop.value = shop
    }

    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

    fun onShopCardClick(
        shopId: String,
        openScreen: (String) -> Unit
    ) = openScreen("$SHOP_SCREEN/$shopId")



}