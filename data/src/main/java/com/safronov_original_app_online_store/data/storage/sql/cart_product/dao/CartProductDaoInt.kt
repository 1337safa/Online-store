package com.safronov_original_app_online_store.data.storage.sql.cart_product.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.safronov_original_app_online_store.data.storage.models.cart_item.CartProductEntity
import com.safronov_original_app_online_store.data.storage.models.consts.TableNames
import kotlinx.coroutines.flow.Flow

@Dao
interface CartProductDaoInt {

    @Insert
    fun insertProductToCart(cartProductEntity: CartProductEntity)

    @Query("SELECT * FROM ${TableNames.CART_ITEMS_TABLE_NAME}")
    fun getAllCartItems(): Flow<List<CartProductEntity>>

    @Query("SELECT * FROM ${TableNames.CART_ITEMS_TABLE_NAME} WHERE productId = :productId")
    fun getCartItemById(productId: String): CartProductEntity?

    @Delete
    fun deleteCartItem(cartProductEntity: CartProductEntity)

}