package com.safronov_original_app_online_store.data.network.dummy_api.product

import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.RetrofitProduct
import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.RetrofitProductInt
import com.safronov_original_app_online_store.data.network.exception.NetworkException
import com.safronov_original_app_online_store.domain.model.product.AllProducts
import retrofit2.Response

class NetworkProductApiIntImpl(
    private val retrofitProduct: RetrofitProduct
): NetworkProductApiInt {

    override suspend fun getAllProducts(): AllProducts? {
        try {
            val service: RetrofitProductInt = retrofitProduct.getService()
            val response: Response<AllProducts> = service.getAllProducts()
            if (response.isSuccessful) {
                return response.body()
            } else {
                throw NetworkException("Error code received from the server")
            }
        } catch (e: Exception) {
            throw NetworkException("Network error or other exception", e)
        }
    }

}