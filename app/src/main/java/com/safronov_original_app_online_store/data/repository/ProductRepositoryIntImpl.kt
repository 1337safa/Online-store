package com.safronov_original_app_online_store.data.repository

import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiInt
import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt

class ProductRepositoryIntImpl(
    private val networkProductApiInt: NetworkProductApiInt
): ProductRepositoryInt {

    override suspend fun getAllProducts(): AllProducts? {
        return networkProductApiInt.getAllProducts()
    }

}