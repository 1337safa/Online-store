package com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.view_model

import androidx.lifecycle.ViewModel
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.model.product.ProductInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FragmentProductDetailsVM(): ViewModel() {

    private val _currentProduct = MutableStateFlow<Product?>(null)
    val currentProduct = _currentProduct.asStateFlow()

    private var currentProductInfo: List<ProductInfo> = emptyList()

    fun saveCurrentProduct(product: Product?) {
        this._currentProduct.value = product
    }

    fun saveCurrentProductInfo(list: List<ProductInfo>) {
        currentProductInfo = list
    }

    fun getCurrentProductInfo() = currentProductInfo

}