package com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.rcv

import com.safronov_original_app_online_store.domain.model.product.SelectedProduct

interface RcvSelectedProductsInt {

    fun onSelectedProductClick(selectedProduct: SelectedProduct)

}