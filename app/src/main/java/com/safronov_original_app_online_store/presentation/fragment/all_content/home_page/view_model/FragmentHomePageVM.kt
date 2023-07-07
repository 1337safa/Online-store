package com.safronov_original_app_online_store.presentation.fragment.all_content.home_page.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun insertSelectedProduct(product: Product) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val selectedProduct = productConverter.convertProductToSelectedProduct(product)
                    productsServiceInt.insertSelectedProduct(selectedProduct)
                } catch (e: Exception) {
                    logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    fun loadAllProducts() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val selectedProductCategory =
                        productCategoriesServiceInt.getSelectedProductCategory()
                    if (selectedProductCategory.productCategory == null) {
                        _allProducts.value = productsServiceInt.getAllProducts()
                    } else {
                        _allProducts.value =
                            productsServiceInt.getAllProductsByCategory(category = selectedProductCategory)
                    }
                } catch (e: Exception) {
                    logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

}