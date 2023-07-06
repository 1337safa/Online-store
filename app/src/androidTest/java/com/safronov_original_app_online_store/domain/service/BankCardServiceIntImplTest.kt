package com.safronov_original_app_online_store.domain.service

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.safronov_original_app_online_store.data.repository.BankCardRepositoryIntImpl
import com.safronov_original_app_online_store.data.storage.models.converters.BankCardConverter
import com.safronov_original_app_online_store.data.storage.sql.AppStorage
import com.safronov_original_app_online_store.data.storage.sql.bank_card.StorageBankCardApiIntImpl
import com.safronov_original_app_online_store.data.storage.sql.bank_card.dao.BankCardDaoInt
import com.safronov_original_app_online_store.domain.model.bank_card.BankCard
import com.safronov_original_app_online_store.domain.service.bank_card.BankCardServiceInt
import com.safronov_original_app_online_store.domain.service.bank_card.BankCardServiceIntImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BankCardServiceIntImplTest {

    private lateinit var bankCardDaoInt: BankCardDaoInt
    private lateinit var appStorage: AppStorage
    private lateinit var bankCardServiceInt: BankCardServiceInt

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appStorage = Room.inMemoryDatabaseBuilder(context = context, AppStorage::class.java).build()
        bankCardDaoInt = appStorage.getBankCardDaoInt()
        val storageBankCardApiInt = StorageBankCardApiIntImpl(
            bankCardDaoInt = bankCardDaoInt
        )
        val bankCardRepositoryInt = BankCardRepositoryIntImpl(
            bankCardConverter = BankCardConverter(),
            storageBankCardApiInt = storageBankCardApiInt
        )
        bankCardServiceInt = BankCardServiceIntImpl(
            bankCardRepositoryInt = bankCardRepositoryInt
        )
    }

    @After
    fun closeDb() {
        appStorage.close()
    }

    @Test
    fun insertUserBankCard() = runBlocking {
        val testBankCard = BankCard(
            cardNumber = 4787384,
            CVC = "9101",
            validity = "23/03"
        )
        bankCardServiceInt.insertUserBankCard(bankCard = testBankCard)
        val result = bankCardServiceInt.getAllUserBankCards()
        val item = result.first().first()
        Assert.assertTrue(testBankCard.cardNumber == item.cardNumber)
    }

}