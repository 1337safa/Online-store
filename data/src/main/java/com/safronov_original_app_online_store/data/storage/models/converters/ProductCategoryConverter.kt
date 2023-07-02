package com.safronov_original_app_online_store.data.storage.models.converters

import com.safronov_original_app_online_store.data.storage.models.SelectedProductCategoryEntity
import com.safronov_original_app_online_store.domain.model.product_category.SelectedProductCategory

class ProductCategoryConverter {

    fun convertSelectedProductCategoryToSelectedProductCategoryEntity(
        selectedProductCategory: SelectedProductCategory
    ): SelectedProductCategoryEntity {
        return SelectedProductCategoryEntity(
            productCategory = selectedProductCategory.productCategory
        )
    }

    fun convertSelectedProductCategoryEntityToSelectedProductCategory(selectedProductCategoryEntity: SelectedProductCategoryEntity): SelectedProductCategory {
        return SelectedProductCategory(
            productCategory = selectedProductCategoryEntity.productCategory
        )
    }

}