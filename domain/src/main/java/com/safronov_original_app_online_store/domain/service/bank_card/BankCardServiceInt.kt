package com.safronov_original_app_online_store.domain.service.bank_card

import com.safronov_original_app_online_store.domain.model.bank_card.BankCard
import kotlinx.coroutines.flow.Flow

interface BankCardServiceInt {

    suspend fun insertUserBankCard(bankCard: BankCard)
    suspend fun getAllUserBankCards(): Flow<List<BankCard>>

}