package com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.product_purchase.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.data.storage.models.converters.CartProductConverter
import com.safronov_original_app_online_store.domain.model.bank_card.BankCard
import com.safronov_original_app_online_store.domain.model.cart.CartProduct
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.service.bank_card.BankCardServiceInt
import com.safronov_original_app_online_store.domain.service.cart.CartServiceInt
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FragmentProductPurchaseVM(
    private val bankCardServiceInt: BankCardServiceInt,
    private val productsServiceInt: ProductsServiceInt,
    private val cartServiceInt: CartServiceInt,
    private val cartProductConverter: CartProductConverter
) : ViewModel() {

    private val _allUserBankCards = MutableStateFlow<List<BankCard>?>(null)
    val allUserBankCards = _allUserBankCards.asStateFlow()
    private val _currentProduct = MutableStateFlow<Product?>(null)
    val currentProduct = _currentProduct.asStateFlow()

    private var selectedBankCard: BankCard? = null

    private var primaryKeyOfCartProduct: String? = null

    fun loadAllUserBankCards() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    bankCardServiceInt.getAllUserBankCards().collect {
                        _allUserBankCards.value = it
                    }
                } catch (e: Exception ) {
                    logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    fun loadCurrentProductById(productId: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _currentProduct.value = productsServiceInt.getProductById(productId = productId)
                } catch (e: Exception) {
                    logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    fun deleteCurrentProductFromCart(product: Product) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val cartProduct: CartProduct =
                        cartProductConverter.convertProductToCartProduct(product)
                    cartProduct.primaryKey = primaryKeyOfCartProduct?.toIntOrNull()
                    cartServiceInt.deleteCartItem(cartProduct = cartProduct)
                } catch (e: Exception) {
                    logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    fun savePrimaryKeyOfCartProduct(primaryKey: String) {
        primaryKeyOfCartProduct = primaryKey
    }

    fun saveSelectedBankCard(bankCard: BankCard) {
        selectedBankCard = bankCard
    }

    fun getSelectedBankCard() = selectedBankCard

}