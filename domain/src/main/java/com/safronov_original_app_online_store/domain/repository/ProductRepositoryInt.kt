package com.safronov_original_app_online_store.domain.repository

import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.ProductCategories
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import com.safronov_original_app_online_store.domain.model.product_category.SelectedProductCategory
import kotlinx.coroutines.flow.Flow

interface ProductRepositoryInt {

    suspend fun getProductsCategories(): ProductCategories?
    suspend fun getAllProducts(): AllProducts?
    suspend fun getAllProductsByCategory(category: SelectedProductCategory): AllProducts?
    suspend fun insertSelectedProduct(selectedProduct: SelectedProduct)
    suspend fun getAllSelectedProducts(): Flow<List<SelectedProduct>>
    suspend fun getSelectedProductById(productId: String): SelectedProduct
    suspend fun deleteSelectedProduct(selectedProduct: SelectedProduct)
    suspend fun getSelectedProductsByTitle(productTitle: String): List<SelectedProduct>
    suspend fun getAllProductsBySearch(searchText: String): AllProducts?

}