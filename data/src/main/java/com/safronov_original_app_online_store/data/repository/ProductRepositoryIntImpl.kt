package com.safronov_original_app_online_store.data.repository

import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiInt
import com.safronov_original_app_online_store.data.storage.selected_item_history.product.StorageProductApiInt
import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.ProductCategories
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt
import kotlinx.coroutines.flow.Flow

class ProductRepositoryIntImpl(
    private val networkProductApiInt: NetworkProductApiInt,
    private val storageProductApiInt: StorageProductApiInt
): ProductRepositoryInt {

    override suspend fun getProductsCategories(): ProductCategories? {
        return networkProductApiInt.getProductsCategories()
    }

    override suspend fun getAllProducts(): AllProducts? {
        return networkProductApiInt.getAllProducts()
    }

    override suspend fun insertSelectedProduct(selectedProduct: SelectedProduct) {
        storageProductApiInt.insertSelectedProduct(selectedProduct)
    }

    override suspend fun getAllSelectedProducts(): Flow<List<SelectedProduct>> {
        return storageProductApiInt.getAllSelectedProducts()
    }

    override suspend fun getSelectedProductById(productId: String): SelectedProduct {
        return storageProductApiInt.getSelectedProductById(productId = productId)
    }

    override suspend fun deleteSelectedProduct(selectedProduct: SelectedProduct) {
        storageProductApiInt.deleteSelectedProduct(selectedProduct = selectedProduct)
    }

    override suspend fun getSelectedProductsByTitle(productTitle: String): List<SelectedProduct> {
        return storageProductApiInt.getSelectedProductsByTitle(productTitle = productTitle)
    }

}