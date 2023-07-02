package com.safronov_original_app_online_store.domain.service

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiInt
import com.safronov_original_app_online_store.data.repository.ProductRepositoryIntImpl
import com.safronov_original_app_online_store.data.storage.models.converters.ProductConverter
import com.safronov_original_app_online_store.data.storage.sql.selected_product.StorageProductApiInt
import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.ProductCategories
import com.safronov_original_app_online_store.domain.model.product_category.SelectedProductCategory
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceIntImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.anyOrNull

class ProductsServiceIntImplTest {

    @Test
    fun `getProductsCategories, should return all products categories`(): Unit = runBlocking{
        val testProductCategories = ProductCategories()
        testProductCategories.add("Something")
        testProductCategories.add("Phones")
        testProductCategories.add("Laptops")

        val rep = mock(ProductRepositoryInt::class.java)
        Mockito.`when`(rep.getProductsCategories()).thenReturn(testProductCategories)
        val productsService = ProductsServiceIntImpl(productRepositoryInt = rep)
        val categories: ProductCategories? = productsService.getProductsCategories()

        println("Products categories: ${categories}")

    }

    @Test
    fun `getAllProducts, should return all products`() = runBlocking {
        val testAllProducts = AllProducts(
            limit = 1,
            products = emptyList(),
            skip = 0,
            total = 0
        )

        val rep = mock(ProductRepositoryInt::class.java)
        Mockito.`when`(rep.getAllProducts()).thenReturn(testAllProducts)
        val productsService = ProductsServiceIntImpl(productRepositoryInt = rep)
        val allProducts = productsService.getAllProducts()

        println("AllProducts: $allProducts")
        Assert.assertTrue(allProducts == testAllProducts)
    }

    @Test
    fun getAllProductsByCategory_ShouldReturnAllProductsBySomeProductCategory() = runBlocking {
        val testAllProducts = AllProducts(
            limit = 1,
            products = emptyList(),
            skip = 0,
            total = 0
        )

        val rep = mock(ProductRepositoryInt::class.java)
        Mockito.`when`(rep.getAllProductsByCategory(anyOrNull())).thenReturn(testAllProducts)

        val productsService = ProductsServiceIntImpl(productRepositoryInt = rep)
        val allProducts = productsService.getAllProductsByCategory(SelectedProductCategory("SomeCategory"))

        println("AllProducts: $allProducts")
        Assert.assertTrue(allProducts == testAllProducts)
    }

}