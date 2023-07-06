package com.safronov_original_app_online_store.data.storage.models.converters

import com.safronov_original_app_online_store.data.storage.models.bank_card.BankCardEntity
import com.safronov_original_app_online_store.domain.model.bank_card.BankCard

class BankCardConverter {

    fun convertBankCardToBankCardEntity(
        bankCard: BankCard
    ): BankCardEntity {
        return BankCardEntity(
            cardNumber = bankCard.cardNumber,
            validity = bankCard.validity,
            CVC = bankCard.CVC,
            primaryKey = bankCard.primaryKey
        )
    }

    fun convertListOfBankCardsEntityToListOfBankCards(
        list: List<BankCardEntity>
    ): List<BankCard> {
        val mList = mutableListOf<BankCard>()
        list.forEach {
            mList.add(
                BankCard(
                    cardNumber = it.cardNumber,
                    validity = it.validity,
                    CVC = it.CVC,
                    primaryKey = it.primaryKey
                )
            )
        }
        return mList.toList()
    }

}