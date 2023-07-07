package com.safronov_original_app_online_store.presentation.fragment.all_content.online_search_product.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.data.storage.models.converters.ProductConverter
import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FragmentOnlineProductSearchVM(
    private val productsServiceInt: ProductsServiceInt,
    private val productConverter: ProductConverter
): ViewModel() {

    private val _allProducts = MutableStateFlow<AllProducts?>(null)
    val allProducts = _allProducts.asStateFlow()
    private var currentSearchText: String? = null

    fun saveCurrentSearchText(searchText: String) { currentSearchText = searchText }

    fun getCurrentSearchText() = currentSearchText

    fun insertSelectedProduct(product: Product) {
        try {
            val selectedProduct = productConverter.convertProductToSelectedProduct(product = product)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    productsServiceInt.insertSelectedProduct(selectedProduct)
                } catch (e: Exception) {
                    logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    fun loadAllProductsBySearch(searchText: String) {
        try {
            viewModelScope.launch(Dispatchers.Main) {
                try {
                    _allProducts.value =
                        productsServiceInt.getAllProductsBySearch(searchText = searchText)
                } catch (e: Exception) {
                    logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

}