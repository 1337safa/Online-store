package com.safronov_original_app_online_store.data.storage.models.bank_card

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.safronov_original_app_online_store.data.storage.models.consts.TableNames
    
@Entity(tableName = TableNames.BANK_CARDS_TABLE_NAME)
data class BankCardEntity(
    @ColumnInfo val cardNumber: Long?,
    @ColumnInfo val validity: String?,
    @ColumnInfo val CVC: String?,
    @PrimaryKey(autoGenerate = true) val primaryKey: Int? = null
)
