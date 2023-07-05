package com.safronov_original_app_online_store.data.storage.sql.selected_product

import com.safronov_original_app_online_store.data.storage.exception.StorageException
import com.safronov_original_app_online_store.data.storage.models.product.SelectedProductEntity
import com.safronov_original_app_online_store.data.storage.sql.selected_product.dao.ProductDaoInt
import kotlinx.coroutines.flow.Flow

class StorageProductApiIntImpl(
    private val productDaoInt: ProductDaoInt,
): StorageProductApiInt {

    override suspend fun insertSelectedProduct(selectedProduct: SelectedProductEntity) {
        try {
            val potentialSelectedProductExist = productDaoInt.getSelectedProductById(selectedProduct.productId.toString())
            if (potentialSelectedProductExist == null) {
                productDaoInt.insertSelectedProduct(selectedProduct)
            }
        } catch (e: Exception) {
            throw StorageException("Storage exception when inserting new selection product", e)
        }
    }

    override suspend fun getAllSelectedProducts(): Flow<List<SelectedProductEntity>> {
        try {
            return productDaoInt.getAllSelectedProducts()
        } catch (e: Exception) {
            throw StorageException("Storage exception when getting all selected products", e)
        }
    }

    override suspend fun getSelectedProductById(productId: String): SelectedProductEntity? {
        try {
            return productDaoInt.getSelectedProductById(productId = productId)
        } catch (e: Exception) {
            throw StorageException("Storage exception when getting selected product by id", e)
        }
    }

    override suspend fun deleteSelectedProduct(selectedProduct: SelectedProductEntity) {
        try {
            productDaoInt.deleteSelectedProduct(selectedProductEntity = selectedProduct)
        } catch (e: Exception) {
            throw StorageException("Storage exception when deleting selected product", e)
        }
    }

    override suspend fun getSelectedProductsByTitle(productTitle: String): List<SelectedProductEntity> {
        try {
            return productDaoInt.getSelectedProductsByTitle(productTitle = productTitle)
        } catch (e: Exception) {
            throw StorageException("Storage exception when getting selected products", e)
        }
    }

}