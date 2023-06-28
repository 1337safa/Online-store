package com.safronov_original_app_online_store.domain.service.product

import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt
import kotlinx.coroutines.flow.Flow

class ProductsServiceIntImpl(
    private val productRepositoryInt: ProductRepositoryInt
): ProductsServiceInt {

    override suspend fun getAllProducts(): AllProducts? {
        return productRepositoryInt.getAllProducts()
    }

    override suspend fun insertSelectedProduct(selectedProduct: SelectedProduct) {
        productRepositoryInt.insertSelectedProduct(selectedProduct)
    }

    override suspend fun getAllSelectedProducts(): Flow<List<SelectedProduct>> {
        return productRepositoryInt.getAllSelectedProducts()
    }

    override suspend fun getSelectedProductById(productId: String): SelectedProduct {
        return productRepositoryInt.getSelectedProductById(productId = productId)
    }

    override suspend fun deleteSelectedProduct(selectedProduct: SelectedProduct) {
        productRepositoryInt.deleteSelectedProduct(selectedProduct = selectedProduct)
    }

    override suspend fun getSelectedProductsByTitle(productTitle: String): List<SelectedProduct> {
        return productRepositoryInt.getSelectedProductsByTitle(productTitle = productTitle)
    }

}