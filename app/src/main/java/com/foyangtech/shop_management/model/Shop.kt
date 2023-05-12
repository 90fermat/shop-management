package com.foyangtech.shop_management.model

import com.google.firebase.firestore.DocumentId

data class Shop(
    @DocumentId val id: String = "",
    val name: String = "",
    val description: String = "",
    val currency: String = ""
)
