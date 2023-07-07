package com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.rcv

import com.safronov_original_app_online_store.domain.model.cart.CartProduct

interface RcvAllCartItemsInt {

    fun onCartItemClick(cartProduct: CartProduct)

}