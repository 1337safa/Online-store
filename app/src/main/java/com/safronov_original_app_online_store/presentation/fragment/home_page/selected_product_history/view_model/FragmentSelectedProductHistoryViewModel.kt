package com.safronov_original_app_online_store.presentation.fragment.home_page.selected_product_history.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FragmentSelectedProductHistoryViewModel(
    private val productsServiceInt: ProductsServiceInt
): ViewModel() {

    private val _allSelectedProducts = MutableStateFlow<List<SelectedProduct>?>(null)
    val allSelectedProducts = _allSelectedProducts.asStateFlow()
    private var currentSearchText: String? = null

    fun saveCurrentSearchText(searchText: String) { currentSearchText = searchText }

    fun getCurrentSearchText() = currentSearchText

    fun loadAllSelectedProducts() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                productsServiceInt.getAllSelectedProducts().collect {
                    _allSelectedProducts.value = it.reversed()
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    fun loadSelectedProductsByTitle(productTitle: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                _allSelectedProducts.value = productsServiceInt.getSelectedProductsByTitle(productTitle = productTitle)
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

}