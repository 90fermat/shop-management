package com.foyangtech.shop_management.model.service

import com.foyangtech.shop_management.model.Product
import com.foyangtech.shop_management.model.Shop
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val shops: Flow<List<Shop>>

    suspend fun getAllShops(): Flow<List<Shop>>
    suspend fun getShop(shopId: String): Shop?
    suspend fun saveShop(shop: Shop): String
    suspend fun updateShop(shop: Shop)
    suspend fun deleteShop(shopId: String)

    suspend fun getProductsFromShop(shopId: String): Flow<List<Product>>
    suspend fun getProductFromShop(shopId: String, productId: String): Shop?
    suspend fun saveProductToShop(shopId: String, product: Product): String
    suspend fun updateProductFromShop(shopId: String, product: Product)
    suspend fun deleteProductFromShop(shopId: String, productId: String)
}
