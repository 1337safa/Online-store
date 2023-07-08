package com.safronov_original_app_online_store.presentation.fragment.all_content.user_profile.user_bank_cards.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.domain.model.bank_card.BankCard
import com.safronov_original_app_online_store.domain.service.bank_card.BankCardServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FragmentUserBankCardsVM(
    private val bankCardsServiceInt: BankCardServiceInt
): ViewModel() {

    private val _allUserBankCards = MutableStateFlow<List<BankCard>?>(null)
    val allUserBankCards = _allUserBankCards.asStateFlow()

    fun loadAllUserBankCards() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                bankCardsServiceInt.getAllUserBankCards().collect {
                    _allUserBankCards.value = it
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

}