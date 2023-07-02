package com.safronov_original_app_online_store.domain.service.product_category

import com.safronov_original_app_online_store.domain.model.product_category.SelectedProductCategory

interface ProductCategoriesServiceInt {

    suspend fun insertSelectedProductCategory(selectedProductCategory: SelectedProductCategory)
    suspend fun getSelectedProductCategory(): SelectedProductCategory

}