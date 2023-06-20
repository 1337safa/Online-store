package com.safronov_original_app_online_store.presentation.fragment.home_page.all_products.rcv

import com.safronov_original_app_online_store.domain.model.product.Product

interface RcvAllProductsInt {
    fun onProductClick(product: Product)
}