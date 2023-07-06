package com.safronov_original_app_online_store.data.storage.sql.bank_card

import com.safronov_original_app_online_store.data.storage.exception.StorageException
import com.safronov_original_app_online_store.data.storage.models.bank_card.BankCardEntity
import com.safronov_original_app_online_store.data.storage.sql.bank_card.dao.BankCardDaoInt
import kotlinx.coroutines.flow.Flow

class StorageBankCardApiIntImpl(
    private val bankCardDaoInt: BankCardDaoInt
): StorageBankCardApiInt {

    override suspend fun insertUserBankCard(bankCardEntity: BankCardEntity) {
        bankCardDaoInt.insertUserBankCard(bankCardEntity = bankCardEntity)
    }
    
    override suspend fun getAllUserBankCards(): Flow<List<BankCardEntity>> {
        try {
            return bankCardDaoInt.getAllUserBankCards()
        } catch (e: Exception) {
            throw StorageException("Storage exception when getting all user bank cards, ${e.message}", e)
        }
    }

}