package com.safronov_original_app_online_store.presentation.fragment.home_page.product_cart.view_model

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

    fun loadAllCartItems() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val allCartItems = cartServiceInt.getAllCartItems()
                allCartItems.collect {
                    _allCartItems.value = it.reversed()
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

}