package com.safronov_original_app_online_store.presentation.fragment.home_page.home_page.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.service.product.interfaces.ProductsServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FragmentHomePageVM(
    private val productsServiceInt: ProductsServiceInt
): ViewModel() {

    private val _allProducts = MutableStateFlow<AllProducts?>(null)
    val allProducts = _allProducts.asStateFlow()

    var positionOfRecyclerView = 0

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _allProducts.value = productsServiceInt.getAllProducts()
        }
    }

}