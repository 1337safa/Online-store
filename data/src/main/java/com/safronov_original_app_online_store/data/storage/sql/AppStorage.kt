package com.safronov_original_app_online_store.data.storage.sql

import androidx.room.Database
import androidx.room.RoomDatabase
import com.safronov_original_app_online_store.data.storage.models.cart_item.CartProductEntity
import com.safronov_original_app_online_store.data.storage.models.product.SelectedProductEntity
import com.safronov_original_app_online_store.data.storage.sql.cart_product.dao.CartProductDaoInt
import com.safronov_original_app_online_store.data.storage.sql.selected_product.dao.ProductDaoInt

@Database(entities = [SelectedProductEntity::class, CartProductEntity::class], version = 2)
abstract class AppStorage(): RoomDatabase() {

    abstract fun getProductDaoInt(): ProductDaoInt
    abstract fun getCartDaoInt(): CartProductDaoInt

    companion object {
        const val BASE_APP_STORAGE_NAME = "Online-store database.db"
    }

}