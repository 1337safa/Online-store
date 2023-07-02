package com.safronov_original_app_online_store.presentation.fragment.home_page.home_page.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.core.extensions.logD
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.data.storage.models.converters.ProductConverter
import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import com.safronov_original_app_online_store.domain.service.product_category.ProductCategoriesServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FragmentHomePageVM(
    private val productsServiceInt: ProductsServiceInt,
    private val productConverter: ProductConverter,
    private val productCategoriesServiceInt: ProductCategoriesServiceInt
) : ViewModel() {

    private val _allProducts = MutableStateFlow<AllProducts?>(null)
    val allProducts = _allProducts.asStateFlow()

    private var currentSearchText: String? = null

    fun saveCurrentSearchText(searchText: String) { currentSearchText = searchText }

    fun getCurrentSearchText() = currentSearchText

    fun insertSelectedProduct(product: Product) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val selectedProduct = productConverter.convertProductToSelectedProduct(product)
                productsServiceInt.insertSelectedProduct(selectedProduct)
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    fun loadAllProducts() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val selectedProductCategory =
                    productCategoriesServiceInt.getSelectedProductCategory()
                if (selectedProductCategory.productCategory == null) {
                    _allProducts.value = productsServiceInt.getAllProducts()
                } else {
                    _allProducts.value =
                        productsServiceInt.getAllProductsByCategory(category = selectedProductCategory)
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    fun loadAllProductsBySearch(searchText: String) {
        try {
            viewModelScope.launch(Dispatchers.Main) {
                _allProducts.value =
                    productsServiceInt.getAllProductsBySearch(searchText = searchText)
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

}