package com.safronov_original_app_online_store.data.storage.models.converters

import com.safronov_original_app_online_store.data.storage.models.SelectedProductEntity
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct

class ProductConverter {

    fun convertSelectedProductEntityToSelectedProduct(
        selectedProductEntity: SelectedProductEntity
    ): SelectedProduct {
        return SelectedProduct(
            id = selectedProductEntity.id
        )
    }

    fun convertSelectedProductToSelectedProductEntity(selectedProduct: SelectedProduct): SelectedProductEntity {
        return SelectedProductEntity(
            id = selectedProduct.id
        )
    }

    fun convertListOfSelectedProductEntityToListOfSelectedProduct(
        list: List<SelectedProductEntity>
    ): List<SelectedProduct> {
        val mList = mutableListOf<SelectedProduct>()
        list.forEach {
            mList.add(SelectedProduct(id = it.id))
        }
        return mList.toList()
    }

}