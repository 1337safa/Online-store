package com.safronov_original_app_online_store.presentation.fragment.all_content.product_category.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.domain.model.product.ProductCategories
import com.safronov_original_app_online_store.domain.model.product_category.SelectedProductCategory
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import com.safronov_original_app_online_store.domain.service.product_category.ProductCategoriesServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FragmentProductCategoryVM(
    private val productsServiceInt: ProductsServiceInt,
    private val productCategoriesServiceInt: ProductCategoriesServiceInt
): ViewModel() {

    private val _productCategories = MutableStateFlow<ProductCategories?>(null)
    val productCategories = _productCategories.asStateFlow()

    fun loadProductsCategories() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _productCategories.value = productsServiceInt.getProductsCategories()
                } catch (e: Exception) {
                    logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    fun getSelectedProductCategory(result: (SelectedProductCategory) -> Unit) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    result.invoke(productCategoriesServiceInt.getSelectedProductCategory())
                } catch (e: Exception) {
                    logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    fun insertSelectedProductCategory(category: String, isChecked: Boolean) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val selectedProductCategory = SelectedProductCategory()
                    if (isChecked) {
                        selectedProductCategory.productCategory = category
                    } else {
                        selectedProductCategory.productCategory = null
                    }
                    productCategoriesServiceInt.insertSelectedProductCategory(selectedProductCategory)
                } catch (e: Exception) {
                    logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

}