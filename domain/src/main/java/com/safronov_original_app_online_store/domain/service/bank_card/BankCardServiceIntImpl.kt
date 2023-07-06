package com.safronov_original_app_online_store.domain.service.bank_card

import com.safronov_original_app_online_store.domain.model.bank_card.BankCard
import com.safronov_original_app_online_store.domain.repository.BankCardRepositoryInt
import kotlinx.coroutines.flow.Flow

class BankCardServiceIntImpl(
    private val bankCardRepositoryInt: BankCardRepositoryInt
): BankCardServiceInt {

    override suspend fun insertUserBankCard(bankCard: BankCard) {
        bankCardRepositoryInt.insertUserBankCard(bankCard = bankCard)
    }

    override suspend fun getAllUserBankCards(): Flow<List<BankCard>> {
        return bankCardRepositoryInt.getAllUserBankCards()
    }

}