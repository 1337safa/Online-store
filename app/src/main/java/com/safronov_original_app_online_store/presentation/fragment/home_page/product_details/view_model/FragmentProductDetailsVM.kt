package com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.model.product.ProductInfo
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FragmentProductDetailsVM(
    private val productsServiceInt: ProductsServiceInt
): ViewModel() {

    private val _currentProduct = MutableStateFlow<Product?>(null)
    val currentProduct = _currentProduct.asStateFlow()
    private val _allSelectedProduct = MutableStateFlow<List<SelectedProduct>?>(null)
    val allSelectedProduct = _allSelectedProduct.asStateFlow()

    private var currentProductInfo: List<ProductInfo> = emptyList()

    fun saveCurrentProduct(product: Product?) {
        this._currentProduct.value = product
    }

    fun saveCurrentProductInfo(list: List<ProductInfo>) {
        currentProductInfo = list
    }

    fun getCurrentProductInfo() = currentProductInfo

    fun loadAllSelectedProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productsServiceInt.getAllSelectedProducts().collect { listOfSelectedProduct ->
                _allSelectedProduct.value = listOfSelectedProduct.reversed()
            }
        }
    }

}