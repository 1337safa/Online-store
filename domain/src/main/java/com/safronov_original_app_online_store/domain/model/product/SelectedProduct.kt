package com.safronov_original_app_online_store.domain.model.product

data class SelectedProduct(
    val productId: String,
    val price: Int,
    val thumbnail: String,
    val title: String,
    /** [primaryKey] is used to store the identifier in the local SQL database,
     * no need to change its value unless the new value is null
     * and [primaryKey] does not contain any other data */
    val primaryKey: Int?
)
