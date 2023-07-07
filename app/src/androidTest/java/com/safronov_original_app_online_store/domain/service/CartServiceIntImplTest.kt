package com.safronov_original_app_online_store.domain.service

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.safronov_original_app_online_store.data.repository.CartRepositoryIntImpl
import com.safronov_original_app_online_store.data.storage.models.converters.CartProductConverter
import com.safronov_original_app_online_store.data.storage.sql.AppStorage
import com.safronov_original_app_online_store.data.storage.sql.cart_product.StorageCartApiIntImpl
import com.safronov_original_app_online_store.data.storage.sql.cart_product.dao.CartProductDaoInt
import com.safronov_original_app_online_store.domain.model.cart.CartProduct
import com.safronov_original_app_online_store.domain.repository.CartRepositoryInt
import com.safronov_original_app_online_store.domain.service.cart.CartServiceInt
import com.safronov_original_app_online_store.domain.service.cart.CartServiceIntImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CartServiceIntImplTest {

    private lateinit var cartProductDaoInt: CartProductDaoInt
    private lateinit var appStorage: AppStorage
    private lateinit var cartServiceInt: CartServiceInt

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appStorage = Room.inMemoryDatabaseBuilder(context, AppStorage::class.java).build()
        cartProductDaoInt = appStorage.getCartDaoInt()
        val storageCartApiInt = StorageCartApiIntImpl(
            cartProductDaoInt = cartProductDaoInt
        )
        val cartRepository: CartRepositoryInt = CartRepositoryIntImpl(
            cartProductConverter = CartProductConverter(),
            storageCartApiInt = storageCartApiInt
        )
        cartServiceInt = CartServiceIntImpl(
            cartRepositoryInt = cartRepository
        )
    }

    @After
    fun closeDb() {
        appStorage.close()
    }

    @Test
    fun insertProductToCart() = runBlocking {
        val cartProduct = CartProduct(
            productId = "something",
            price = 4,
            thumbnail = "url...",
            title = "hello, world!",
            primaryKey = null
        )
        cartServiceInt.insertProductToCart(cartProduct = cartProduct)
        val cartItems = cartServiceInt.getAllCartItems()
        val result: List<CartProduct> = cartItems.first()
        Log.d("iLog", "Current item: ${cartProduct}, new item: ${result.first()}")
        Assert.assertTrue(cartProduct.title == result.first().title)
    }

    @Test
    fun deleteCartItem() = runBlocking {
        val cartProduct = CartProduct(
            productId = "something",
            price = 4,
            thumbnail = "url...",
            title = "hello, world!",
            primaryKey = null
        )
        cartServiceInt.insertProductToCart(cartProduct = cartProduct)
        val allCartItems: Flow<List<CartProduct>> = cartServiceInt.getAllCartItems()
        val result: List<CartProduct> = allCartItems.first()
        Assert.assertTrue(cartProduct.title == result.first().title)
        cartServiceInt.deleteCartItem(cartProduct = result.first())
        val secondResult = cartServiceInt.getAllCartItems().first()
        Assert.assertTrue(secondResult.isEmpty())
    }

    @Test
    fun getAllCartProductEntitiesByProductTitle() = runBlocking {
        val cartProduct = CartProduct(
            productId = "something",
            price = 4,
            thumbnail = "url...",
            title = "hello, world!",
            primaryKey = null
        )
        cartServiceInt.insertProductToCart(cartProduct = cartProduct)
        val allItems = cartServiceInt.getAllCartProductEntitiesByProductTitle(productTitle = "hello, world")
        Assert.assertTrue(allItems.first().title == cartProduct.title)
    }

}