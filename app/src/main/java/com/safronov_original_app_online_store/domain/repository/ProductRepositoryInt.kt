package com.safronov_original_app_online_store.domain.repository

import com.safronov_original_app_online_store.domain.model.product.AllProducts

interface ProductRepositoryInt {

    suspend fun getAllProducts(): AllProducts?

}