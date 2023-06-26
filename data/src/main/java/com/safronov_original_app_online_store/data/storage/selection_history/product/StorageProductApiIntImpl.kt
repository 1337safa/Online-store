package com.safronov_original_app_online_store.data.storage.selection_history.product

import com.safronov_original_app_online_store.data.storage.exception.StorageException
import com.safronov_original_app_online_store.data.storage.models.converters.ProductConverter
import com.safronov_original_app_online_store.data.storage.selection_history.product.dao.ProductDaoInt
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
            productDaoInt.insertSelectedProduct(selectedProductEntity)
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

}