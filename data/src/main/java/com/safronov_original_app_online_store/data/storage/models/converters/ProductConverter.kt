package com.safronov_original_app_online_store.data.storage.models.converters

import com.safronov_original_app_online_store.data.storage.models.product.SelectedProductEntity
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct

class ProductConverter {

    fun convertSelectedProductEntityToSelectedProduct(
        selectedProductEntity: SelectedProductEntity?
    ): SelectedProduct {
        return SelectedProduct(
            productId = selectedProductEntity?.productId.toString(),
            price = selectedProductEntity?.price ?: 0,
            thumbnail = selectedProductEntity?.thumbnail ?: "",
            title = selectedProductEntity?.title.toString(),
            primaryKey = selectedProductEntity?.primaryKey
        )
    }

    fun convertSelectedProductToSelectedProductEntity(selectedProduct: SelectedProduct): SelectedProductEntity {
        return SelectedProductEntity(
            productId = selectedProduct.productId,
            price = selectedProduct.price,
            thumbnail = selectedProduct.thumbnail,
            title = selectedProduct.title,
            primaryKey = selectedProduct.primaryKey
        )
    }

    fun convertListOfSelectedProductEntityToListOfSelectedProduct(
        list: List<SelectedProductEntity>
    ): List<SelectedProduct> {
        val mList = mutableListOf<SelectedProduct>()
        list.forEach {
            mList.add(
                SelectedProduct(
                    productId = it.productId.toString(),
                    price = it.price ?: 0,
                    thumbnail = it.thumbnail.toString(),
                    title = it.title.toString(),
                    primaryKey = it.primaryKey
                )
            )
        }
        return mList.toList()
    }

    fun convertProductToSelectedProduct(product: Product): SelectedProduct {
        return SelectedProduct(
            productId = product.id.toString(),
            price = product.price,
            thumbnail = product.thumbnail,
            title = product.title,
            primaryKey = null
        )
    }

}