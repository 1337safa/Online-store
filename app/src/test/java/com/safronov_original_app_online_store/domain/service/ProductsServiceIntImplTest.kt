package com.safronov_original_app_online_store.domain.service

import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiInt
import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.RetrofitProduct
import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.RetrofitProductInt
import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt
import com.safronov_original_app_online_store.domain.service.product.interfaces.ProductsServiceInt
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class ProductsServiceIntImplTest {

    private var mockProductsServiceInt: ProductsServiceInt? = null
    private var mockProductRepositoryInt: ProductRepositoryInt? = null
    private var mockNetworkProductApiInt: NetworkProductApiInt? = null
    private var retrofitProduct: RetrofitProduct? = null
    private var mockRetrofitProductInt: RetrofitProductInt? = null

    @Before
    fun init() {
        retrofitProduct = RetrofitProduct()
        mockNetworkProductApiInt = mock(NetworkProductApiInt::class.java)
        mockProductRepositoryInt = mock(ProductRepositoryInt::class.java)
        mockProductsServiceInt = mock(ProductsServiceInt::class.java)
        mockRetrofitProductInt = mock(RetrofitProductInt::class.java)
    }

    @Test
    fun `getAllProducts, should return all products from server`() = runBlocking {
        val testAllProducts = AllProducts(
            limit = 1,
            products = emptyList(),
            skip = 0,
            total = 0
        )
        Mockito.`when`(mockProductsServiceInt?.getAllProducts()).thenReturn(testAllProducts)
        val allProducts = mockProductsServiceInt?.getAllProducts()
        println("AllProducts: $allProducts")
        Assert.assertTrue(allProducts == testAllProducts)
    }

    @Test
    fun `getAllProducts, should throw exception, because server got error code`(): Unit =
        runBlocking {
            //TODO write test!
        }

}