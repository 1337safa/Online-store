package com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit

import com.safronov_original_app_online_store.domain.model.product.AllProducts
import retrofit2.Response
import retrofit2.http.GET

interface ProductRetrofitInt {

    @GET("/products")
    suspend fun getAllProducts(): Response<AllProducts>

}