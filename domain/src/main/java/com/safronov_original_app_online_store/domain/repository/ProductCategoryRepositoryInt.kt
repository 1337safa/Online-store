package com.safronov_original_app_online_store.domain.repository

import com.safronov_original_app_online_store.domain.model.product_category.SelectedProductCategory

interface ProductCategoryRepositoryInt {

    suspend fun insertSelectedProductCategory(selectedProductCategory: SelectedProductCategory)
    suspend fun getSelectedProductCategory(): SelectedProductCategory

}