package com.safronov_original_app_online_store.data.repository

import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiInt
import com.safronov_original_app_online_store.data.storage.models.converters.ProductConverter
import com.safronov_original_app_online_store.data.storage.sql.selected_product.StorageProductApiInt
import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.model.product.ProductCategories
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import com.safronov_original_app_online_store.domain.model.product_category.SelectedProductCategory
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryIntImpl(
    private val networkProductApiInt: NetworkProductApiInt,
    private val storageProductApiInt: StorageProductApiInt,
    private val productConverter: ProductConverter
) : ProductRepositoryInt {

    override suspend fun getProductsCategories(): ProductCategories? {
        return networkProductApiInt.getProductsCategories()
    }

    override suspend fun getAllProducts(): AllProducts? {
        return networkProductApiInt.getAllProducts()
    }

    override suspend fun getAllProductsByCategory(category: SelectedProductCategory): AllProducts? {
        return networkProductApiInt.getAllProductsByCategory(category = category.productCategory.toString())
    }

    override suspend fun getProductById(productId: String): Product? {
        return networkProductApiInt.getProductById(productId = productId)
    }

    override suspend fun insertSelectedProduct(selectedProduct: SelectedProduct) {
        storageProductApiInt.insertSelectedProduct(
            productConverter.convertSelectedProductToSelectedProductEntity(selectedProduct = selectedProduct)
        )
    }

    override suspend fun getAllSelectedProducts(): Flow<List<SelectedProduct>> {
        val allSelectedProductEntityFlow = storageProductApiInt.getAllSelectedProducts()
        val selectedProductFlow: Flow<List<SelectedProduct>> = flow {
            allSelectedProductEntityFlow.collect { listOfSelectedProductEntity ->
                val listOfSelectedProduct =
                    productConverter.convertListOfSelectedProductEntityToListOfSelectedProduct(
                        listOfSelectedProductEntity
                    )
                emit(listOfSelectedProduct)
            }
        }
        return selectedProductFlow
    }

    override suspend fun getSelectedProductById(productId: String): SelectedProduct {
        return productConverter.convertSelectedProductEntityToSelectedProduct(storageProductApiInt.getSelectedProductById(productId = productId))
    }

    override suspend fun deleteSelectedProduct(selectedProduct: SelectedProduct) {
        storageProductApiInt.deleteSelectedProduct(productConverter.convertSelectedProductToSelectedProductEntity(selectedProduct = selectedProduct))
    }

    override suspend fun getSelectedProductsByTitle(productTitle: String): List<SelectedProduct> {
        return productConverter.convertListOfSelectedProductEntityToListOfSelectedProduct(storageProductApiInt.getSelectedProductsByTitle(productTitle = productTitle))
    }

    override suspend fun getAllProductsBySearch(searchText: String): AllProducts? {
        return networkProductApiInt.getAllProductsBySearch(searchText = searchText)
    }

}