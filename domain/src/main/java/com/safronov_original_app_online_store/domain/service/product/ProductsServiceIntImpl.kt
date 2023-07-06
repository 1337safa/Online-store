package com.safronov_original_app_online_store.domain.service.product

import com.safronov_original_app_online_store.domain.model.product.AllProducts
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.model.product.ProductCategories
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import com.safronov_original_app_online_store.domain.model.product_category.SelectedProductCategory
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt
import kotlinx.coroutines.flow.Flow

class ProductsServiceIntImpl(
    private val productRepositoryInt: ProductRepositoryInt
): ProductsServiceInt {

    override suspend fun addNewProduct(newProduct: Product): Product? {
        return productRepositoryInt.addNewProduct(newProduct = newProduct)
    }

    override suspend fun getProductsCategories(): ProductCategories? {
        return productRepositoryInt.getProductsCategories()
    }

    override suspend fun getAllProducts(): AllProducts? {
        return productRepositoryInt.getAllProducts()
    }

    override suspend fun getAllProductsByCategory(category: SelectedProductCategory): AllProducts? {
        return productRepositoryInt.getAllProductsByCategory(category = category)
    }

    override suspend fun getProductById(productId: String): Product? {
        return productRepositoryInt.getProductById(productId = productId)
    }

    override suspend fun insertSelectedProduct(selectedProduct: SelectedProduct) {
        val potentialSelectedProductExist = productRepositoryInt.getSelectedProductById(selectedProduct.productId)
        if (potentialSelectedProductExist == null) {
            productRepositoryInt.insertSelectedProduct(selectedProduct)
        }
    }

    override suspend fun getAllSelectedProducts(): Flow<List<SelectedProduct>> {
        return productRepositoryInt.getAllSelectedProducts()
    }

    override suspend fun getSelectedProductById(productId: String): SelectedProduct? {
        return productRepositoryInt.getSelectedProductById(productId = productId)
    }

    override suspend fun deleteSelectedProduct(selectedProduct: SelectedProduct) {
        productRepositoryInt.deleteSelectedProduct(selectedProduct = selectedProduct)
    }

    override suspend fun getAllProductsBySearch(searchText: String): AllProducts? {
        return productRepositoryInt.getAllProductsBySearch(searchText = searchText)
    }

    override suspend fun getSelectedProductsByTitle(productTitle: String): List<SelectedProduct> {
        return productRepositoryInt.getSelectedProductsByTitle(productTitle = productTitle)
    }

}