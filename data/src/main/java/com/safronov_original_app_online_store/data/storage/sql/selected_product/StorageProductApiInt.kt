package com.safronov_original_app_online_store.data.storage.sql.selected_product

import com.safronov_original_app_online_store.data.storage.models.product.SelectedProductEntity
import kotlinx.coroutines.flow.Flow

interface StorageProductApiInt {

    suspend fun insertSelectedProduct(selectedProduct: SelectedProductEntity)
    suspend fun getAllSelectedProducts(): Flow<List<SelectedProductEntity>>
    suspend fun getSelectedProductById(productId: String): SelectedProductEntity?
    suspend fun deleteSelectedProduct(selectedProduct: SelectedProductEntity)
    suspend fun getSelectedProductsByTitle(productTitle: String): List<SelectedProductEntity>

}