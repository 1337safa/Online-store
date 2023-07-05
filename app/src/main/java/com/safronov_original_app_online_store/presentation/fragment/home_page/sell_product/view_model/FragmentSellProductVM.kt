package com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentSellProductVM(
    private val productsServiceInt: ProductsServiceInt
): ViewModel() {

    var currentProductMainPhoto: String? = null
    var currentSecondaryProductPhotos: List<String>? = null

    fun addNewProduct(newProduct: Product, result: (Product?) -> Unit) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val addedProduct = productsServiceInt.addNewProduct(newProduct = newProduct)
                result.invoke(addedProduct)
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

}