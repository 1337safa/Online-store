package com.safronov_original_app_online_store.domain.service.product.imp

import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt
import com.safronov_original_app_online_store.domain.service.product.interfaces.ProductServiceInt

class ProductServiceIntImpl(
    private val productRepositoryInt: ProductRepositoryInt
): ProductServiceInt {

    override suspend fun getAllProducts(): AllProducts? {
        return productRepositoryInt.getAllProducts()
    }

}