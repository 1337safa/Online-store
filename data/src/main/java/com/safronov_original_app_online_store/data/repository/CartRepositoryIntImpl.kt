package com.safronov_original_app_online_store.data.repository

import com.safronov_original_app_online_store.data.storage.models.converters.CartProductConverter
import com.safronov_original_app_online_store.data.storage.sql.cart_product.StorageCartApiInt
import com.safronov_original_app_online_store.domain.model.cart.CartProduct
import com.safronov_original_app_online_store.domain.repository.CartRepositoryInt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CartRepositoryIntImpl(
    private val cartProductConverter: CartProductConverter,
    private val storageCartApiInt: StorageCartApiInt
): CartRepositoryInt {

    override suspend fun insertProductToCart(cartProduct: CartProduct) {
        storageCartApiInt.insertProductToCart(cartProductEntity = cartProductConverter.convertCartProductToCartProductEntity(cartProduct = cartProduct))
    }

    override suspend fun getAllCartItems(): Flow<List<CartProduct>> {
        val cartFlow = flow<List<CartProduct>> {
            val result = storageCartApiInt.getAllCartItems()
            result?.collect {
                emit(cartProductConverter.convertListOfCartProductEntityToListOfCartProduct(list = it))
            }
        }
        return cartFlow
    }

    override suspend fun deleteCartItem(cartProduct: CartProduct) {
        storageCartApiInt.deleteCartItem(cartProductEntity = cartProductConverter.convertCartProductToCartProductEntity(cartProduct = cartProduct))
    }

}