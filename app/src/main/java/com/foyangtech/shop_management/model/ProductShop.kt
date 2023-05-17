package com.foyangtech.shop_management.model

data class ProductShop(
     val shopId: String,
     val product: Product,
     val stock: Double,
     val shopPrice: Double
)
