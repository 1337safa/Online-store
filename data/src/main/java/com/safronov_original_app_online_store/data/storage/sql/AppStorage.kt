package com.safronov_original_app_online_store.data.storage.sql

import androidx.room.Database
import androidx.room.RoomDatabase
import com.safronov_original_app_online_store.data.storage.models.SelectedProductEntity
import com.safronov_original_app_online_store.data.storage.selection_history.product.dao.ProductDaoInt

@Database(entities = [SelectedProductEntity::class], version = 1)
abstract class AppStorage(): RoomDatabase() {

    abstract fun getProductDaoInt(): ProductDaoInt

    companion object {
        const val BASE_APP_STORAGE_NAME = "Online-store database.db"
    }

}