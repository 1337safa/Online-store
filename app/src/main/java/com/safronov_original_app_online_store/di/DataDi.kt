package com.safronov_original_app_online_store.di

import androidx.room.Room
import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiInt
import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiIntImpl
import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.ProductRetrofit
import com.safronov_original_app_online_store.data.repository.ProductRepositoryIntImpl
import com.safronov_original_app_online_store.data.storage.models.converters.ProductConverter
import com.safronov_original_app_online_store.data.storage.selected_item_history.product.StorageProductApiInt
import com.safronov_original_app_online_store.data.storage.selected_item_history.product.StorageProductApiIntImpl
import com.safronov_original_app_online_store.data.storage.selected_item_history.product.dao.ProductDaoInt
import com.safronov_original_app_online_store.data.storage.sql.AppStorage
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataDi = module {

    single<ProductRepositoryInt> {
        ProductRepositoryIntImpl(networkProductApiInt = get(), storageProductApiInt = get())
    }

    single<NetworkProductApiInt> {
        NetworkProductApiIntImpl(productRetrofit = get())
    }

    single<StorageProductApiInt> {
        StorageProductApiIntImpl(
            productConverter = get(),
            productDaoInt = get()
        )
    }

    single<ProductDaoInt> {
        val db = Room.databaseBuilder(
            context = androidApplication().applicationContext,
            AppStorage::class.java,
            AppStorage.BASE_APP_STORAGE_NAME
        ).build()
        db.getProductDaoInt()
    }

    single {
        ProductConverter()
    }

    single<ProductRetrofit> {
        ProductRetrofit()
    }

}