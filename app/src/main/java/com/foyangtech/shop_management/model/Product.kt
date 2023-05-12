package com.foyangtech.shop_management.model

import com.google.firebase.firestore.DocumentId

data class Product(
    @DocumentId val id: String = "",
    val name: String = "",
    val price: Double = 0.0
)
