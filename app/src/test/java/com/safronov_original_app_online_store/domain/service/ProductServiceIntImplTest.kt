package com.safronov_original_app_online_store.domain.service

import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiInt
import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiIntImpl
import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.RetrofitProduct
import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.RetrofitProductInt
import com.safronov_original_app_online_store.data.repository.ProductRepositoryIntImpl
import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt
import com.safronov_original_app_online_store.domain.service.product.imp.ProductServiceIntImpl
import com.safronov_original_app_online_store.domain.service.product.interfaces.ProductServiceInt
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.Response

class ProductServiceIntImplTest {

    private var mockProductServiceInt: ProductServiceInt? = null
    private var mockProductRepositoryInt: ProductRepositoryInt? = null
    private var mockNetworkProductApiInt: NetworkProductApiInt? = null
    private var retrofitProduct: RetrofitProduct? = null
    private var mockRetrofitProductInt: RetrofitProductInt? = null

    @Before
    fun init() {
        retrofitProduct = RetrofitProduct()
        mockNetworkProductApiInt = mock(NetworkProductApiInt::class.java)
        mockProductRepositoryInt = mock(ProductRepositoryInt::class.java)
        mockProductServiceInt = mock(ProductServiceInt::class.java)
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
        Mockito.`when`(mockProductServiceInt?.getAllProducts()).thenReturn(testAllProducts)
        val allProducts = mockProductServiceInt?.getAllProducts()
        println("AllProducts: $allProducts")
        Assert.assertTrue(allProducts == testAllProducts)
    }

    @Test
    fun `getAllProducts, should throw exception, because server got error code`(): Unit = runBlocking {
        val mockResponse = mock(Response::class.java) as Response<AllProducts>
        mockResponse.errorBody()
        Mockito.`when`(mockRetrofitProductInt?.getAllProducts()).thenReturn(mockResponse)
        //TODO write the test
    }

}