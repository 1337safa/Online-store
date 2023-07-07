package com.safronov_original_app_online_store.domain.service.cart

import com.safronov_original_app_online_store.domain.model.cart.CartProduct
import com.safronov_original_app_online_store.domain.repository.CartRepositoryInt
import kotlinx.coroutines.flow.Flow

class CartServiceIntImpl(
    private val cartRepositoryInt: CartRepositoryInt
): CartServiceInt {

    override suspend fun insertProductToCart(cartProduct: CartProduct) {
        val theSameItemExists = cartRepositoryInt.getCartItemById(productId = cartProduct.productId.toString())
        if (theSameItemExists == null) {
            cartRepositoryInt.insertProductToCart(cartProduct = cartProduct)
        }
    }

    override suspend fun getCartItemById(productId: String): CartProduct? {
        return cartRepositoryInt.getCartItemById(productId = productId)
    }

    override suspend fun getAllCartItems(): Flow<List<CartProduct>> {
        return cartRepositoryInt.getAllCartItems()
    }

    override suspend fun deleteCartItem(cartProduct: CartProduct) {
        cartRepositoryInt.deleteCartItem(cartProduct = cartProduct)
    }

    override suspend fun getAllCartProductEntitiesByProductTitle(productTitle: String): List<CartProduct> {
        return cartRepositoryInt.getAllCartProductEntitiesByProductTitle(productTitle = productTitle)
    }

}