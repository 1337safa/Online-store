package com.safronov_original_app_online_store.data.repository

import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiInt
import com.safronov_original_app_online_store.data.storage.selection_history.product.StorageProductApiInt
import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt
import kotlinx.coroutines.flow.Flow

class ProductRepositoryIntImpl(
    private val networkProductApiInt: NetworkProductApiInt,
    private val storageProductApiInt: StorageProductApiInt
): ProductRepositoryInt {

    override suspend fun getAllProducts(): AllProducts? {
        return networkProductApiInt.getAllProducts()
    }

    override suspend fun insertSelectedProduct(selectedProduct: SelectedProduct) {
        storageProductApiInt.insertSelectedProduct(selectedProduct)
    }

    override suspend fun getAllSelectedProducts(): Flow<List<SelectedProduct>> {
        return storageProductApiInt.getAllSelectedProducts()
    }

}