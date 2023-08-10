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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl
@Inject
constructor(private val firestore: FirebaseFirestore, private val auth: AccountService) :
  StorageService {

  /**
   *  Get all shops for the current User
   */
  @OptIn(ExperimentalCoroutinesApi::class)
  override val shops: Flow<List<Shop>>
    get() =
      auth.currentUser.flatMapLatest { user ->
        currentShopCollection(user.id).snapshots().map { snapshot -> snapshot.toObjects() }
      }

  /**
   *  Get all shops for the current User
   */
  override suspend fun getAllShops(): Flow<List<Shop>> =
    currentShopCollection(auth.currentUserId).snapshots().map { snapshot -> snapshot.toObjects() }


  /**
   *  Get all products from a shop
   *  @param shopId The Id of the Shop where the product reside
   */
  override suspend fun getProductsFromShop(shopId: String): Flow<List<Product>> =
    currentShopProductCollection(auth.currentUserId, shopId).snapshots().map {
      snapshot -> snapshot.toObjects()
    }

  /**
   *  Get all products from a shop
   *  @param shopId The Id of the Shop where the product reside
   */
  override fun getShopProducts(shopId: String): Flow<List<Product>> =
    currentShopProductCollection(auth.currentUserId, shopId).snapshots().map {
      snapshot -> snapshot.toObjects()
    }

  /**
   *  Get a product from a shop
   *  @param shopId The Id of the Shop where the product reside
   *  @param productId The Id of the product to delete
   */
  override suspend fun getProductFromShop(shopId: String, productId: String): Product? =
    currentShopProductCollection(auth.currentUserId, shopId)
      .document(productId).get().await().toObject()

  /**
   *  Save a product in a shop
   *  @param shopId The Id of the Shop where the to save
   *  @param product The product to save
   */
  override suspend fun saveProductToShop(shopId: String, product: Product): String =
    trace(SAVE_PRODUCT_TRACE) {
      currentShopProductCollection(auth.currentUserId, shopId).add(product).await().id
    }

  /**
   *  Update a product in a shop
   *  @param shopId The Id of the Shop where the product reside
   *  @param product The updated product with its same Id
   */
  override suspend fun updateProductFromShop(shopId: String, product: Product) {
    trace(UPDATE_PRODUCT_TRACE) {
      currentShopProductCollection(auth.currentUserId, shopId).document(product.id)
        .set(product).await()
    }
  }

  /**
   *  Delete a product in a shop
   *  @param shopId The Id of the Shop where the product reside
   *  @param productId The Id of the product to delete
   */
  override suspend fun deleteProductFromShop(shopId: String, productId: String) {
    currentShopProductCollection(auth.currentUserId, shopId).document(productId).delete().await()
  }

  /**
   *  Get a shop by its Id
   *  @param shopId The Id of the Shop to get
   */
  override suspend fun getShop(shopId: String): Shop? =
    currentShopCollection(auth.currentUserId).document(shopId).get().await().toObject()

  /**
   *  Update a shop
   *  @param shop The Shop to save
   */
  override suspend fun saveShop(shop: Shop): String =
    trace(SAVE_SHOP_TRACE) { currentShopCollection(auth.currentUserId).add(shop).await().id }

  /**
   *  Update a shop
   *  @param shop The new Shop with the id of the shop to update
   */
  override suspend fun updateShop(shop: Shop): Unit =
    trace(UPDATE_SHOP_TRACE) {
      currentShopCollection(auth.currentUserId).document(shop.id).set(shop).await()
    }

  /**
   *  Delete a Shop by its Id
   */
  override suspend fun deleteShop(shopId: String) {
    currentShopCollection(auth.currentUserId).document(shopId).delete().await()
  }

  /**
   *  Get the shop collection of a user by userId
   */
  private fun currentShopCollection(uid: String): CollectionReference =
    firestore.collection(USER_COLLECTION).document(uid).collection(SHOP_COLLECTION)

  /**
   *  Get the product collection of a shop by owner's Id and the shop Id
   */
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
