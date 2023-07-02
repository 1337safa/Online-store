package com.safronov_original_app_online_store.domain.service.product_category

import com.safronov_original_app_online_store.domain.model.product_category.SelectedProductCategory
import com.safronov_original_app_online_store.domain.repository.ProductCategoryRepositoryInt

class ProductCategoriesServiceIntImpl(
    private val productCategoryRepositoryInt: ProductCategoryRepositoryInt
): ProductCategoriesServiceInt {

    override suspend fun insertSelectedProductCategory(selectedProductCategory: SelectedProductCategory) {
        productCategoryRepositoryInt.insertSelectedProductCategory(selectedProductCategory)
    }

    override suspend fun getSelectedProductCategory(): SelectedProductCategory {
        return productCategoryRepositoryInt.getSelectedProductCategory()
    }

}