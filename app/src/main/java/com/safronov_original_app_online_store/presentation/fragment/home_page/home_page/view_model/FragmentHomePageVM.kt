package com.safronov_original_app_online_store.presentation.fragment.home_page.home_page.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.data.storage.models.converters.ProductConverter
import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FragmentHomePageVM(
    private val productsServiceInt: ProductsServiceInt,
    private val productConverter: ProductConverter
): ViewModel() {

    private val _allProducts = MutableStateFlow<AllProducts?>(null)
    val allProducts = _allProducts.asStateFlow()

    var positionOfRecyclerView = 0

    fun insertSelectedProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedProduct = productConverter.convertProductToSelectedProduct(product)
            productsServiceInt.insertSelectedProduct(selectedProduct)
        }
    }

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _allProducts.value = productsServiceInt.getAllProducts()
        }
    }

}