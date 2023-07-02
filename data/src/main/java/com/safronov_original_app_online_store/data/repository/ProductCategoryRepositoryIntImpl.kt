package com.safronov_original_app_online_store.data.repository

import com.safronov_original_app_online_store.data.storage.shared_preferences.selected_product_category.StorageSelectedProductCategoryApiInt
import com.safronov_original_app_online_store.domain.model.product_category.SelectedProductCategory
import com.safronov_original_app_online_store.domain.repository.ProductCategoryRepositoryInt

class ProductCategoryRepositoryIntImpl(
    private val storageSelectedProductCategoryApiInt: StorageSelectedProductCategoryApiInt
): ProductCategoryRepositoryInt {

    override suspend fun insertSelectedProductCategory(selectedProductCategory: SelectedProductCategory) {
        storageSelectedProductCategoryApiInt.insertSelectedProductCategory(selectedProductCategory)
    }

    override suspend fun getSelectedProductCategory(): SelectedProductCategory {
        return SelectedProductCategory(
            productCategory = storageSelectedProductCategoryApiInt.getSelectedProductCategory()
        )
    }

}