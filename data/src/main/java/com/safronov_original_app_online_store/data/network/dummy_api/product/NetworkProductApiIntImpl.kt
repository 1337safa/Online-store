package com.safronov_original_app_online_store.data.network.dummy_api.product

import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.ProductRetrofitInt
import com.safronov_original_app_online_store.data.network.exception.NetworkException
import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.ProductCategories
import retrofit2.Response

class NetworkProductApiIntImpl(
    private val productRetrofitInt: ProductRetrofitInt
): NetworkProductApiInt {

    override suspend fun getAllProducts(): AllProducts? {
        try {
            val response: Response<AllProducts> = productRetrofitInt.getAllProducts()
            if (response.isSuccessful) {
                return response.body()
            } else {
                throw NetworkException("Error code received from the server")
            }
        } catch (e: Exception) {
            throw NetworkException("Network error or other exception when getting all products", e)
        }
    }

    override suspend fun getProductsCategories(): ProductCategories? {
        try {
            val response = productRetrofitInt.getProductsCategories()
            if (response.isSuccessful) {
                return response.body()
            } else {
                throw NetworkException("Error code received from the server", Exception("result code: ${response.code()}"))
            }
        } catch (e: Exception) {
            throw NetworkException("Network error or something, when getting all products categories, ${e.message}", e)
        }
    }

}