package com.safronov_original_app_online_store.presentation.fragment.home_page.product_cart.rcv

import com.safronov_original_app_online_store.domain.model.cart.CartProduct

interface RcvAllCartItemsInt {

    fun onCartItemClick(cartProduct: CartProduct)

}