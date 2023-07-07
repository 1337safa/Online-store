package com.safronov_original_app_online_store.data.repository

import com.safronov_original_app_online_store.data.storage.models.converters.BankCardConverter
import com.safronov_original_app_online_store.data.storage.sql.bank_card.StorageBankCardApiInt
import com.safronov_original_app_online_store.domain.model.bank_card.BankCard
import com.safronov_original_app_online_store.domain.repository.BankCardRepositoryInt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BankCardRepositoryIntImpl(
    private val storageBankCardApiInt: StorageBankCardApiInt,
    private val bankCardConverter: BankCardConverter
): BankCardRepositoryInt {

    override suspend fun insertUserBankCard(bankCard: BankCard) {
        storageBankCardApiInt.insertUserBankCard(bankCardEntity = bankCardConverter.convertBankCardToBankCardEntity(bankCard = bankCard))
    }

    override suspend fun getAllUserBankCards(): Flow<List<BankCard>> {
        val flowOfListOfBankCards = flow<List<BankCard>> {
            val result = storageBankCardApiInt.getAllUserBankCards()
            result.collect { bankCardsEntities ->
                emit(bankCardConverter.convertListOfBankCardsEntityToListOfBankCards(bankCardsEntities))
            }
        }
        return flowOfListOfBankCards
    }

    override suspend fun getUserBankCardByCardNumber(cardNumber: String): BankCard? {
        return bankCardConverter.convertBankCardEntityToBankCard(storageBankCardApiInt.getUserBankCardByCardNumber(cardNumber = cardNumber))
    }

}