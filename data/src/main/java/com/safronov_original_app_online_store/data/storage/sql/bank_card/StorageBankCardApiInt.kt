package com.safronov_original_app_online_store.data.storage.sql.bank_card

import com.safronov_original_app_online_store.data.storage.models.bank_card.BankCardEntity
import kotlinx.coroutines.flow.Flow

interface StorageBankCardApiInt {

    suspend fun insertUserBankCard(bankCardEntity: BankCardEntity)
    suspend fun getAllUserBankCards(): Flow<List<BankCardEntity>>
    suspend fun getUserBankCardByCardNumber(cardNumber: String): BankCardEntity?

}