package com.safronov_original_app_online_store.data.storage.shared_preferences.selected_product_category

import com.safronov_original_app_online_store.domain.model.product_category.SelectedProductCategory

interface StorageSelectedProductCategoryApiInt {

    suspend fun insertSelectedProductCategory(selectedProductCategoryEntity: SelectedProductCategory)
    suspend fun getSelectedProductCategory(): String?

}