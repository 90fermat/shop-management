package com.foyangtech.shop_management.ui.screens.shop.product.edit

data class EditProductUiState(
   var name: String = "",
   val price: Double = 0.0,
   val shopPrice: Double = 0.0,
   val stock: Double = 0.0,
   val unit: String = "unit"
)
