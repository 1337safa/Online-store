package com.safronov_original_app_online_store.data.storage.sql.cart_product

import com.safronov_original_app_online_store.data.storage.models.cart_item.CartProductEntity
import kotlinx.coroutines.flow.Flow

interface StorageCartApiInt {

    suspend fun insertProductToCart(cartProductEntity: CartProductEntity)
    suspend fun getAllCartItems(): Flow<List<CartProductEntity>>?
    suspend fun deleteCartItem(cartProductEntity: CartProductEntity)

}