package com.safronov_original_app_online_store.data.storage.models.cart_item

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.safronov_original_app_online_store.data.storage.models.consts.TableNames

@Entity(tableName = TableNames.CART_ITEMS_TABLE_NAME)
data class CartProductEntity(
    @ColumnInfo val productId: String?,
    @ColumnInfo val price: Int?,
    @ColumnInfo val thumbnail: String?,
    @ColumnInfo val title: String?,
    @PrimaryKey(autoGenerate = true) val primaryKey: Int? = null
)
