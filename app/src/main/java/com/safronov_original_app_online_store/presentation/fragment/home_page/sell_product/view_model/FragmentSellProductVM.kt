package com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.view_model

import androidx.lifecycle.ViewModel
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt

class FragmentSellProductVM(
    private val productsServiceInt: ProductsServiceInt
): ViewModel() {

    var currentProductMainPhoto: String? = null

}