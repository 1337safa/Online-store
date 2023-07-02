package com.safronov_original_app_online_store.data.storage.shared_preferences.selected_product_category

import android.content.SharedPreferences
import com.safronov_original_app_online_store.domain.model.product_category.SelectedProductCategory

private const val KEY_SELECTED_PRODUCT_CATEGORY = "SelectedProductCategory"

class StorageSelectedProductCategoryApiIntImpl(
    private val sharedPreferencesForSelectedProductCategory: SharedPreferences
): StorageSelectedProductCategoryApiInt {

    override suspend fun insertSelectedProductCategory(selectedProductCategoryEntity: SelectedProductCategory) {
        sharedPreferencesForSelectedProductCategory.edit().putString(KEY_SELECTED_PRODUCT_CATEGORY, selectedProductCategoryEntity.productCategory).apply()
    }

    override suspend fun getSelectedProductCategory(): String? {
        println("Core: ${ sharedPreferencesForSelectedProductCategory.getString(KEY_SELECTED_PRODUCT_CATEGORY, null)}")
        return sharedPreferencesForSelectedProductCategory.getString(KEY_SELECTED_PRODUCT_CATEGORY, null)
    }

}