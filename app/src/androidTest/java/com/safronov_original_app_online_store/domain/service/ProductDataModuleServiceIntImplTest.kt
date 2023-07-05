package com.safronov_original_app_online_store.domain.service

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiIntImpl
import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.ProductRetrofit
import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.ProductRetrofitInt
import com.safronov_original_app_online_store.data.repository.ProductRepositoryIntImpl
import com.safronov_original_app_online_store.data.storage.models.converters.ProductConverter
import com.safronov_original_app_online_store.data.storage.sql.selected_product.StorageProductApiIntImpl
import com.safronov_original_app_online_store.data.storage.sql.selected_product.dao.ProductDaoInt
import com.safronov_original_app_online_store.data.storage.sql.AppStorage
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceIntImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

@RunWith(AndroidJUnit4::class)
class ProductDataModuleServiceIntImplTest {

    private lateinit var productDao: ProductDaoInt
    private lateinit var appStorage: AppStorage
    private lateinit var productServiceInt: ProductsServiceInt

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appStorage = Room.inMemoryDatabaseBuilder(context, AppStorage::class.java).build()
        productDao = appStorage.getProductDaoInt()
        val productRetrofit = ProductRetrofit()
        val net = NetworkProductApiIntImpl(
            productRetrofitInt = productRetrofit.getService()
        )
        val storageProductApiInt = StorageProductApiIntImpl(
            productDaoInt = productDao,
        )
        val productRepositoryInt: ProductRepositoryInt = ProductRepositoryIntImpl(
            networkProductApiInt = net,
            storageProductApiInt = storageProductApiInt,
            productConverter = ProductConverter()
        )
        productServiceInt = ProductsServiceIntImpl(productRepositoryInt = productRepositoryInt)
    }

    @After
    fun closeDb() {
        appStorage.close()
    }

    @Test
    fun insertSelectedProduct() = runBlocking {
        val selectedProduct = SelectedProduct(
            productId = "ID",
            price = 0,
            thumbnail = "url",
            title = "Title",
            primaryKey = null
        )
        productServiceInt.insertSelectedProduct(selectedProduct)
        val selectedProducts = productServiceInt.getSelectedProductsByTitle("Title")
        Assert.assertEquals(selectedProducts.first().title, selectedProduct.title)
        productServiceInt.deleteSelectedProduct(selectedProduct)
        Assert.assertTrue(selectedProducts.size - 1 == 0)
    }

    @Test
    fun getAllSelectedProducts() = runBlocking {
        /* TODO write the test for: productServiceInt.getAllSelectedProducts() */
    }

    @Test
    fun getSelectedProductByTitle() = runBlocking {
        val selectedProduct = SelectedProduct(
            productId = "some product id",
            price = 0,
            thumbnail = "url",
            title = "SPT",
            primaryKey = null
        )
        productServiceInt.insertSelectedProduct(selectedProduct)
        val selectedProducts = productServiceInt.getSelectedProductsByTitle("SPT")
        Assert.assertEquals(selectedProducts.first().title, selectedProduct.title)
    }

    @Test
    fun deleteSelectedProduct() = runBlocking {
        val selectedProduct = SelectedProduct(
            productId = "some product id",
            price = 0,
            thumbnail = "url",
            title = "SPT",
            primaryKey = null
        )
        productServiceInt.insertSelectedProduct(selectedProduct)
        val selectedProducts = productServiceInt.getSelectedProductsByTitle("SPT")
        Assert.assertEquals(selectedProducts.first().title, selectedProduct.title)
        productServiceInt.deleteSelectedProduct(selectedProducts.first())
        val updSelectedProducts = productServiceInt.getSelectedProductsByTitle("SPT")
        Assert.assertTrue(updSelectedProducts.isEmpty())
    }

}