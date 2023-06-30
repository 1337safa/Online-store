package com.safronov_original_app_online_store.presentation.fragment.home_page.product_category.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.domain.model.product.ProductCategories
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FragmentProductCategoryViewModel(
    private val productsServiceInt: ProductsServiceInt
): ViewModel() {

    private val _productCategories = MutableStateFlow<ProductCategories?>(null)
    val productCategories = _productCategories.asStateFlow()

    fun loadProductsCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _productCategories.value = productsServiceInt.getProductsCategories()
        }
    }

}