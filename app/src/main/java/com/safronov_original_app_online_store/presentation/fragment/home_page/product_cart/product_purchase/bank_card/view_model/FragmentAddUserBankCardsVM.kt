package com.safronov_original_app_online_store.presentation.fragment.home_page.product_cart.product_purchase.bank_card.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.domain.model.bank_card.BankCard
import com.safronov_original_app_online_store.domain.service.bank_card.BankCardServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentAddUserBankCardsVM(
    private val bankCardsServiceInt: BankCardServiceInt
): ViewModel() {

    fun insertUserBankCard(bankCard: BankCard, bankCardWithTheSameCardNumberExists: () -> Unit, added: () -> Unit) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                bankCardsServiceInt.insertUserBankCard(bankCard = bankCard, bankCardWithTheSameCardNumberExists = {
                    bankCardWithTheSameCardNumberExists.invoke()
                }, added = {
                    added.invoke()
                })
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

}