package com.safronov_original_app_online_store.data.storage.sql.cart_product

import com.safronov_original_app_online_store.data.storage.exception.StorageException
import com.safronov_original_app_online_store.data.storage.models.cart_item.CartProductEntity
import com.safronov_original_app_online_store.data.storage.sql.cart_product.dao.CartProductDaoInt
import com.safronov_original_app_online_store.domain.model.cart.CartProduct
import kotlinx.coroutines.flow.Flow

class StorageCartApiIntImpl(
    private val cartProductDaoInt: CartProductDaoInt
): StorageCartApiInt {

    override suspend fun insertProductToCart(cartProductEntity: CartProductEntity) {
        try {
            cartProductDaoInt.insertProductToCart(cartProductEntity = cartProductEntity)
        } catch (e: Exception) {
            throw StorageException("Storage exception when inserting product to cart, ${e.message}", e)
        }
    }

    override suspend fun getCartItemById(productId: String): CartProductEntity? {
        try {
            return cartProductDaoInt.getCartItemById(productId = productId)
        } catch (e: Exception) {
            throw StorageException("Storage exception when getting cart item by id, ${e.message}", e)
        }
    }

    override suspend fun getAllCartItems(): Flow<List<CartProductEntity>>? {
        try {
            return cartProductDaoInt.getAllCartItems()
        } catch (e: Exception) {
            throw StorageException("Storage exception when getting all cart items, ${e.message}", e)
        }
    }

    override suspend fun deleteCartItem(cartProductEntity: CartProductEntity) {
        try {
            cartProductDaoInt.deleteCartItem(cartProductEntity = cartProductEntity)
        } catch (e: Exception) {
            throw StorageException("Storage exception when deleting cart item, ${e.message}", e)
        }
    }

    override suspend fun getAllCartProductEntitiesByProductTitle(productTitle: String): List<CartProductEntity> {
        try {
            return cartProductDaoInt.getAllCartProductEntities(productTitle = productTitle)
        } catch (e: Exception) {
            throw StorageException("Storage exception when getting all cart items by product title, ${e.message}", e)
        }
    }

}