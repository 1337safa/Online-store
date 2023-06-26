package com.safronov_original_app_online_store.data.network.dummy_api.product

import com.safronov_original_app_online_store.domain.model.product.AllProducts

interface NetworkProductApiInt {

    suspend fun getAllProducts(): AllProducts?

}