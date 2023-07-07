package com.safronov_original_app_online_store.domain.service.cart

import com.safronov_original_app_online_store.domain.model.cart.CartProduct
import kotlinx.coroutines.flow.Flow

interface CartServiceInt {

    suspend fun insertProductToCart(cartProduct: CartProduct)
    suspend fun getCartItemById(productId: String): CartProduct?
    suspend fun getAllCartItems(): Flow<List<CartProduct>>
    suspend fun deleteCartItem(cartProduct: CartProduct)
    suspend fun getAllCartProductEntitiesByProductTitle(productTitle: String): List<CartProduct>

}