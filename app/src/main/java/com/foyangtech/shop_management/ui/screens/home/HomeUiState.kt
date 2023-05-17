package com.foyangtech.shop_management.ui.screens.home

import com.foyangtech.shop_management.model.Shop

data class HomeUiState(
    var showDialogShopName: String = "",
    var showDialogShopDescription: String = "",
    var showDialogShopCurrency: String = "",
    var updatedShopName: String = "",
    var updatedShopDescription: String = "",
    var updatedShopCurrency: String = ""
)
