package com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit

import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.model.product.ProductCategories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductRetrofitInt {

    @GET("/products")
    suspend fun getAllProducts(): Response<AllProducts>

    @GET("/products/categories")
    suspend fun getProductsCategories(): Response<ProductCategories>

    @GET("/products/category/{category}")
    suspend fun getAllProductsByCategory(
        @Path("category") category: String
    ): Response<AllProducts>

    @GET("/products/search")
    suspend fun getAllProductsBySearch(
        @Query("q") searchText: String
    ): Response<AllProducts>

    @GET("/products/{id}")
    suspend fun getProductById(
        @Path("id") productId: String
    ): Response<Product>

}