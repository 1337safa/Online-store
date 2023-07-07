package com.safronov_original_app_online_store.data.storage.models.converters

import com.safronov_original_app_online_store.data.storage.models.cart_item.CartProductEntity
import com.safronov_original_app_online_store.domain.model.cart.CartProduct
import com.safronov_original_app_online_store.domain.model.product.Product

class CartProductConverter {

    fun convertCartProductEntityToCartProduct(cartProductEntity: CartProductEntity?): CartProduct? {
        return if (cartProductEntity != null) {
            CartProduct(
                productId = cartProductEntity.productId,
                price = cartProductEntity.price,
                thumbnail = cartProductEntity.thumbnail,
                title = cartProductEntity.title,
                primaryKey = cartProductEntity.primaryKey
            )
        } else {
            null
        }
    }

    fun convertCartProductToCartProductEntity(cartProduct: CartProduct): CartProductEntity {
        return CartProductEntity(
            productId = cartProduct.productId,
            price = cartProduct.price,
            thumbnail = cartProduct.thumbnail,
            title = cartProduct.title,
            primaryKey = cartProduct.primaryKey
        )
    }

    fun convertListOfCartProductEntityToListOfCartProduct(list: List<CartProductEntity>): List<CartProduct> {
        val mList = mutableListOf<CartProduct>()
        list.forEach {
            mList.add(CartProduct(
                productId = it.productId,
                price = it.price,
                thumbnail = it.thumbnail,
                title = it.title,
                primaryKey = it.primaryKey
            ))
        }
        return mList.toList()
    }

    fun convertProductToCartProduct(product: Product): CartProduct {
        return CartProduct(
            productId = product.id.toString(),
            price = product.price,
            thumbnail = product.thumbnail,
            title = product.title
        )
    }

}