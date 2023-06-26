package com.safronov_original_app_online_store.data.storage.selection_history.product

import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import kotlinx.coroutines.flow.Flow

interface StorageProductApiInt {

    suspend fun insertSelectedProduct(selectedProduct: SelectedProduct)
    suspend fun getAllSelectedProducts(): Flow<List<SelectedProduct>>

}