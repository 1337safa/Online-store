package com.safronov_original_app_online_store.domain.repository

import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import kotlinx.coroutines.flow.Flow

interface ProductRepositoryInt {

    suspend fun getAllProducts(): AllProducts?

    suspend fun insertSelectedProduct(selectedProduct: SelectedProduct)

    suspend fun getAllSelectedProducts(): Flow<List<SelectedProduct>>

}