package com.safronov_original_app_online_store.data.network.dummy_api.product

import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.model.product.ProductCategories
import retrofit2.Response

interface NetworkProductApiInt {

    suspend fun addNewProduct(newProduct: Product): Product?
    suspend fun getAllProducts(): AllProducts?
    suspend fun getProductsCategories(): ProductCategories?
    suspend fun getAllProductsByCategory(category: String): AllProducts?
    suspend fun getAllProductsBySearch(searchText: String): AllProducts?
    suspend fun getProductById(productId: String): Product?

}