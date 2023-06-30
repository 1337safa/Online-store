package com.safronov_original_app_online_store.data.storage.selected_item_history.product

import com.safronov_original_app_online_store.data.storage.exception.StorageException
import com.safronov_original_app_online_store.data.storage.models.converters.ProductConverter
import com.safronov_original_app_online_store.data.storage.selected_item_history.product.dao.ProductDaoInt
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StorageProductApiIntImpl(
   private val productDaoInt: ProductDaoInt,
   private val productConverter: ProductConverter
): StorageProductApiInt {

    override suspend fun insertSelectedProduct(selectedProduct: SelectedProduct) {
        try {
            val selectedProductEntity = productConverter.convertSelectedProductToSelectedProductEntity(selectedProduct = selectedProduct)
            val potentialSelectedProductExist = productDaoInt.getSelectedProductById(selectedProductEntity.productId.toString())
            if (potentialSelectedProductExist == null) {
                productDaoInt.insertSelectedProduct(selectedProductEntity)
            }
        } catch (e: Exception) {
            throw StorageException("Storage exception when inserting new selection product", e)
        }
    }

    override suspend fun getAllSelectedProducts(): Flow<List<SelectedProduct>> {
        try {
            val allSelectedProductEntityFlow = productDaoInt.getAllSelectedProducts()
            val selectedProductFlow: Flow<List<SelectedProduct>> = flow {
                allSelectedProductEntityFlow.collect { listOfSelectedProductEntity ->
                    try {
                        val listOfSelectedProduct = productConverter.convertListOfSelectedProductEntityToListOfSelectedProduct(listOfSelectedProductEntity)
                        emit(listOfSelectedProduct)
                    } catch (e: Exception) {
                        throw StorageException("Storage exception when emit data", e)
                    }
                }
            }
            return selectedProductFlow
        } catch (e: Exception) {
            throw StorageException("Storage exception when getting all selected products", e)
        }
    }

    override suspend fun getSelectedProductById(productId: String): SelectedProduct {
        try {
            val selectedProductEntity = productDaoInt.getSelectedProductById(productId = productId)
            return productConverter.convertSelectedProductEntityToSelectedProduct(selectedProductEntity = selectedProductEntity)
        } catch (e: Exception) {
            throw StorageException("Storage exception when getting selected product by id", e)
        }
    }

    override suspend fun deleteSelectedProduct(selectedProduct: SelectedProduct) {
        try {
            val selectedProductEntity = productConverter.convertSelectedProductToSelectedProductEntity(selectedProduct = selectedProduct)
            productDaoInt.deleteSelectedProduct(selectedProductEntity = selectedProductEntity)
        } catch (e: Exception) {
            throw StorageException("Storage exception when deleting selected product", e)
        }
    }

    override suspend fun getSelectedProductsByTitle(productTitle: String): List<SelectedProduct> {
        try {
            return productConverter.convertListOfSelectedProductEntityToListOfSelectedProduct(
                productDaoInt.getSelectedProductsByTitle(productTitle = productTitle)
            )
        } catch (e: Exception) {
            throw StorageException("Storage exception when getting selected products", e)
        }
    }

}