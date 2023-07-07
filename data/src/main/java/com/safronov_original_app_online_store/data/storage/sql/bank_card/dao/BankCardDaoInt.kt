package com.safronov_original_app_online_store.data.storage.sql.bank_card.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.safronov_original_app_online_store.data.storage.models.bank_card.BankCardEntity
import com.safronov_original_app_online_store.data.storage.models.consts.TableNames
import kotlinx.coroutines.flow.Flow

@Dao
interface BankCardDaoInt {

    @Insert
    fun insertUserBankCard(bankCardEntity: BankCardEntity)

    @Query("SELECT * FROM ${TableNames.BANK_CARDS_TABLE_NAME}")
    fun getAllUserBankCards(): Flow<List<BankCardEntity>>

    @Query("SELECT * FROM ${TableNames.BANK_CARDS_TABLE_NAME} WHERE cardNumber = :cardNumber")
    fun getUserBankCardByCardNumber(cardNumber: String): BankCardEntity?

}