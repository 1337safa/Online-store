package com.safronov_original_app_online_store.domain.service.cart

import com.safronov_original_app_online_store.domain.model.cart.CartProduct
import com.safronov_original_app_online_store.domain.repository.CartRepositoryInt
import kotlinx.coroutines.flow.Flow

class CartServiceIntImpl(
    private val cartRepositoryInt: CartRepositoryInt
): CartServiceInt {

    override suspend fun insertProductToCart(cartProduct: CartProduct) {
        cartRepositoryInt.insertProductToCart(cartProduct = cartProduct)
    }

    override suspend fun getAllCartItems(): Flow<List<CartProduct>> {
        return cartRepositoryInt.getAllCartItems()
    }

    override suspend fun deleteCartItem(cartProduct: CartProduct) {
        cartRepositoryInt.deleteCartItem(cartProduct = cartProduct)
    }

}