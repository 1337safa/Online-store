package com.safronov_original_app_online_store.domain.service.product.interfaces

import com.safronov_original_app_online_store.domain.model.product.AllProducts

interface ProductServiceInt {

    suspend fun getAllProducts(): AllProducts?

}