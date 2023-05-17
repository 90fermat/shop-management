package com.foyangtech.shop_management.ui.screens.shop.product.edit

import androidx.compose.runtime.mutableStateOf
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

   val uiState = mutableStateOf(EditProductUiState())

   fun onNameChange(newValue: String) {
      uiState.value = uiState.value.copy(name = newValue)
   }

   fun onPriceChange(newValue: String) {

   }

}