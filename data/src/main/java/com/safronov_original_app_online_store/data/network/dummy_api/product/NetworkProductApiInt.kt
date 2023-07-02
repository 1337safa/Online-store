package com.safronov_original_app_online_store.data.network.dummy_api.product

import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.ProductCategories
import retrofit2.Response

interface NetworkProductApiInt {

    suspend fun getAllProducts(): AllProducts?
    suspend fun getProductsCategories(): ProductCategories?
    suspend fun getAllProductsByCategory(category: String): AllProducts?

}