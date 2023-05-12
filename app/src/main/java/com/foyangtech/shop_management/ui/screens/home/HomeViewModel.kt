package com.foyangtech.shop_management.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import com.foyangtech.shop_management.util.SETTINGS_SCREEN
import com.foyangtech.shop_management.util.SHOP_SCREEN
import com.foyangtech.shop_management.model.Shop
import com.foyangtech.shop_management.model.service.LogService
import com.foyangtech.shop_management.model.service.StorageService
import com.foyangtech.shop_management.ui.screens.ShopManagementViewModel
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


    val shops = storageService.shops

    fun onShopNameChange(newValue: String) {
        uiState.value = uiState.value.copy(showDialogShopName = newValue)
    }

    fun onShopDescriptionChange(newValue: String) {
        uiState.value = uiState.value.copy(showDialogShopDescription = newValue)
    }

    fun onShopCurrencyChange(newValue: String) {
        uiState.value = uiState.value.copy(showDialogShopCurrency = newValue)
    }

    fun addShop() {
        launchCatching {
            storageService.saveShop(
                Shop("", uiState.value.showDialogShopName,
                    uiState.value.showDialogShopDescription,
                    uiState.value.showDialogShopCurrency
                )
            )
        }

        uiState.value.showDialogShopName = ""
        uiState.value.showDialogShopDescription = ""
        uiState.value.showDialogShopCurrency = ""
    }

    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

    fun onShopCardClick(shopId: String, openScreen: (String) -> Unit) =
        openScreen("$SHOP_SCREEN/$shopId")

}