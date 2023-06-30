package com.safronov_original_app_online_store.domain.service

import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiInt
import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiIntImpl
import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.ProductRetrofit
import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.ProductRetrofitInt
import com.safronov_original_app_online_store.data.repository.ProductRepositoryIntImpl
import com.safronov_original_app_online_store.data.storage.selected_item_history.product.StorageProductApiInt
import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.ProductCategories
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceIntImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.Response
import java.util.WeakHashMap

class ProductsServiceIntImplTest {

    @Test
    fun `getProductsCategories, should return all products categories`(): Unit = runBlocking{
        val testProductCategories = ProductCategories()
        testProductCategories.add("Something")
        testProductCategories.add("Phones")
        testProductCategories.add("Laptops")

        val rep = ProductRepositoryIntImpl(networkProductApiInt = mock(NetworkProductApiInt::class.java), mock(StorageProductApiInt::class.java))
        Mockito.`when`(rep.getProductsCategories()).thenReturn(testProductCategories)
        val productsService = ProductsServiceIntImpl(productRepositoryInt = rep)
        val categories = productsService.getProductsCategories()

        println("Products categories: ${categories}")
        Assert.assertTrue(categories == testProductCategories)
    }

    @Test
    fun `getAllProducts, should return all products`() = runBlocking {
        val testAllProducts = AllProducts(
            limit = 1,
            products = emptyList(),
            skip = 0,
            total = 0
        )

        val rep = ProductRepositoryIntImpl(networkProductApiInt = mock(NetworkProductApiInt::class.java), mock(StorageProductApiInt::class.java))
        Mockito.`when`(rep.getAllProducts()).thenReturn(testAllProducts)
        val productsService = ProductsServiceIntImpl(productRepositoryInt = rep)
        val allProducts = productsService.getAllProducts()

        println("AllProducts: $allProducts")
        Assert.assertTrue(allProducts == testAllProducts)
    }

}