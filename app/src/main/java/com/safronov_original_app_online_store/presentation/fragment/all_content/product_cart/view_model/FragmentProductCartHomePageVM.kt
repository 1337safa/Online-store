package com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.domain.model.cart.CartProduct
import com.safronov_original_app_online_store.domain.service.cart.CartServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FragmentProductCartHomePageVM(
    private val cartServiceInt: CartServiceInt
): ViewModel() {

    private val _allCartItems = MutableStateFlow<List<CartProduct>?>(null)
    val allCartItems = _allCartItems.asStateFlow()

    private var currentSearchText: String? = null
    fun saveCurrentSearchText(searchText: String) { currentSearchText = searchText }
    fun getCurrentSearchText() = currentSearchText

    fun loadAllCartItems() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val allCartItems = cartServiceInt.getAllCartItems()
                    allCartItems.collect {
                        _allCartItems.value = it.reversed()
                    }
                } catch (e: Exception) {
                    logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    fun loadAllCartProductEntitiesByProductTitle(productTitle: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _allCartItems.value = cartServiceInt.getAllCartProductEntitiesByProductTitle(productTitle = productTitle)
                } catch (e: Exception) {
                    logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

}