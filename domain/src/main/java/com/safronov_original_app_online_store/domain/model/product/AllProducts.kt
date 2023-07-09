package com.safronov_original_app_online_store.domain.model.product

data class AllProducts(

    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int

)