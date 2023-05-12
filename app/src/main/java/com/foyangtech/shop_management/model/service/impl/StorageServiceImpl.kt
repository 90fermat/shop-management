package com.foyangtech.shop_management.model.service.impl

import com.foyangtech.shop_management.model.Product
import com.foyangtech.shop_management.model.Shop
import com.foyangtech.shop_management.model.service.AccountService
import com.foyangtech.shop_management.model.service.StorageService
import com.foyangtech.shop_management.model.service.trace
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class StorageServiceImpl
@Inject
constructor(private val firestore: FirebaseFirestore, private val auth: AccountService) :
  StorageService {

  @OptIn(ExperimentalCoroutinesApi::class)
  override val shops: Flow<List<Shop>>
    get() =
      auth.currentUser.flatMapLatest { user ->
        currentShopCollection(user.id).snapshots().map { snapshot -> snapshot.toObjects() }
      }

  override suspend fun getAllShops(): Flow<List<Shop>> =
    currentShopCollection(auth.currentUserId).snapshots().map { snapshot -> snapshot.toObjects() }


  override suspend fun getProductsFromShop(shopId: String): Flow<List<Product>> =
    currentShopProductCollection(auth.currentUserId, shopId).snapshots().map {
      snapshot -> snapshot.toObjects()
    }

  override suspend fun getProductFromShop(shopId: String, productId: String): Shop? =
    currentShopProductCollection(auth.currentUserId, shopId)
      .document(productId).get().await().toObject()

  override suspend fun saveProductToShop(shopId: String, product: Product): String =
    trace(SAVE_PRODUCT_TRACE) {
      currentShopProductCollection(auth.currentUserId, shopId).add(product).await().id
    }

  override suspend fun updateProductFromShop(shopId: String, product: Product) {
    trace(UPDATE_PRODUCT_TRACE) {
      currentShopProductCollection(auth.currentUserId, shopId).document(product.id)
        .set(product).await()
    }
  }

  override suspend fun deleteProductFromShop(shopId: String, productId: String) {
    currentShopProductCollection(auth.currentUserId, shopId).document(productId).delete().await()
  }

  override suspend fun getShop(shopId: String): Shop? =
    currentShopCollection(auth.currentUserId).document(shopId).get().await().toObject()

  override suspend fun saveShop(shop: Shop): String =
    trace(SAVE_SHOP_TRACE) { currentShopCollection(auth.currentUserId).add(shop).await().id }

  override suspend fun updateShop(shop: Shop): Unit =
    trace(UPDATE_SHOP_TRACE) {
      currentShopCollection(auth.currentUserId).document(shop.id).set(shop).await()
    }

  override suspend fun deleteShop(shopId: String) {
    currentShopCollection(auth.currentUserId).document(shopId).delete().await()
  }

  private fun currentShopCollection(uid: String): CollectionReference =
    firestore.collection(USER_COLLECTION).document(uid).collection(SHOP_COLLECTION)

  private fun currentShopProductCollection(userId: String, shopId: String): CollectionReference =
    currentShopCollection(userId).document(shopId).collection(PRODUCT_COLLECTION)

  companion object {
    private const val USER_COLLECTION = "users"
    private const val SHOP_COLLECTION = "shops"
    private const val PRODUCT_COLLECTION = "products"
    private const val SAVE_SHOP_TRACE = "saveShop"
    private const val SAVE_PRODUCT_TRACE = "saveProduct"
    private const val UPDATE_SHOP_TRACE = "updateShop"
    private const val UPDATE_PRODUCT_TRACE = "updateProduct"
  }
}
