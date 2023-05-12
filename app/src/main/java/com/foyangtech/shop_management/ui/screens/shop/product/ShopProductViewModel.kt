package com.foyangtech.shop_management.ui.screens.shop.product

import com.foyangtech.shop_management.model.service.LogService
import com.foyangtech.shop_management.model.service.StorageService
import com.foyangtech.shop_management.ui.screens.ShopManagementViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopProductViewModel@Inject constructor(
    logService: LogService,
    private val storageService: StorageService
) : ShopManagementViewModel(logService) {
}