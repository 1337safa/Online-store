package com.safronov_original_app_online_store.domain.repository

import com.safronov_original_app_online_store.domain.model.bank_card.BankCard
import kotlinx.coroutines.flow.Flow

interface BankCardRepositoryInt {

    suspend fun insertUserBankCard(bankCard: BankCard)
    suspend fun getAllUserBankCards(): Flow<List<BankCard>>
    suspend fun getUserBankCardByCardNumber(cardNumber: String): BankCard?

}