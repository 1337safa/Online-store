package com.safronov_original_app_online_store.data.storage.selection_history.product.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.safronov_original_app_online_store.data.storage.models.SelectedProductEntity
import com.safronov_original_app_online_store.data.storage.models.consts.TableNames
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDaoInt {

    @Insert
    fun insertSelectedProduct(selectedProductEntity: SelectedProductEntity)

    @Query("SELECT * FROM ${TableNames.SELECTED_PRODUCTS_TABLE_NAME}")
    fun getAllSelectedProducts(): Flow<List<SelectedProductEntity>>

}