package com.safronov_original_app_online_store.domain.service

import com.safronov_original_app_online_store.domain.model.product_category.SelectedProductCategory
import com.safronov_original_app_online_store.domain.repository.ProductCategoryRepositoryInt
import com.safronov_original_app_online_store.domain.service.product_category.ProductCategoriesServiceInt
import com.safronov_original_app_online_store.domain.service.product_category.ProductCategoriesServiceIntImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class ProductCategoriesServiceIntImplTest {

    private val productCategoryRepositoryInt = mock(ProductCategoryRepositoryInt::class.java)

    @Test
    fun insertSelectedProductCategory(): Unit = runBlocking {
        val selectedProductCategory = SelectedProductCategory(
            productCategory = "Category"
        )
        Mockito.`when`(productCategoryRepositoryInt.insertSelectedProductCategory(selectedProductCategory = selectedProductCategory)).thenReturn(
            Unit
        )
        val productCategoriesServiceInt: ProductCategoriesServiceInt = ProductCategoriesServiceIntImpl(productCategoryRepositoryInt)
        productCategoriesServiceInt.insertSelectedProductCategory(selectedProductCategory)
    }

    @Test
    fun getSelectedProductCategory(): Unit = runBlocking {
        val selectedProductCategory = SelectedProductCategory(
            productCategory = "something"
        )
        Mockito.`when`(productCategoryRepositoryInt.getSelectedProductCategory()).thenReturn(selectedProductCategory)
        val productCategoriesServiceInt: ProductCategoriesServiceInt = ProductCategoriesServiceIntImpl(productCategoryRepositoryInt)
        val result = productCategoriesServiceInt.getSelectedProductCategory()
        println("Old: ${selectedProductCategory}, new: ${result}")
        Assert.assertTrue(selectedProductCategory.productCategory.toString() == result.productCategory.toString())
    }

}