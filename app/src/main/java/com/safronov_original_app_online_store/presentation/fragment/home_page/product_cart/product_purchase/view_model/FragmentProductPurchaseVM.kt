package com.safronov_original_app_online_store.presentation.fragment.home_page.product_cart.product_purchase.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.domain.model.bank_card.BankCard
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.service.bank_card.BankCardServiceInt
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FragmentProductPurchaseVM(
    private val bankCardServiceInt: BankCardServiceInt,
    private val productsServiceInt: ProductsServiceInt
): ViewModel() {

    private val _allUserBankCards = MutableStateFlow<List<BankCard>?>(null)
    val allUserBankCards = _allUserBankCards.asStateFlow()
    private val _currentProduct = MutableStateFlow<Product?>(null)
    val currentProduct = _currentProduct.asStateFlow()

    fun loadAllUserBankCards() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                bankCardServiceInt.getAllUserBankCards().collect {
                    _allUserBankCards.value = it
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    fun insertUserBankCard(bankCard: BankCard) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                bankCardServiceInt.insertUserBankCard(bankCard = bankCard)
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    fun loadCurrentProductById(productId: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                _currentProduct.value = productsServiceInt.getProductById(productId = productId)
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

}