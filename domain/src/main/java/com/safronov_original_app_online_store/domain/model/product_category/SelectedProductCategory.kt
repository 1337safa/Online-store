package com.safronov_original_app_online_store.domain.model.product_category

data class SelectedProductCategory(
    var productCategory: String? = null
) {
    override fun toString(): String {
        return productCategory.toString()
    }
}
