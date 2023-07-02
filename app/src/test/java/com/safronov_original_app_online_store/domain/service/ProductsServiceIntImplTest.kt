package com.safronov_original_app_online_store.domain.service

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
    fun `getAllProductsByCategory should return all products by some product category`() = runBlocking {
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

    @Test
    fun `getAllProductsBySearch, should return all products by some search text`() = runBlocking {
        val testAllProducts = AllProducts(
            limit = 1,
            products = emptyList(),
            skip = 0,
            total = 0
        )

        val rep = mock(ProductRepositoryInt::class.java)
        Mockito.`when`(rep.getAllProductsBySearch(anyOrNull())).thenReturn(testAllProducts)

        val productsService = ProductsServiceIntImpl(productRepositoryInt = rep)
        val allProducts = productsService.getAllProductsBySearch(searchText = "iPhone...")

        println("AllProducts: $allProducts")
        Assert.assertTrue(allProducts == testAllProducts)
    }

}