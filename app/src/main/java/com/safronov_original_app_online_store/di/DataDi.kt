package com.safronov_original_app_online_store.di

import androidx.room.Room
import com.safronov_original_app_online_store.data.network.dummy_api.base_info.DummyApiBaseInfo
import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiInt
import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiIntImpl
import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.ProductRetrofit
import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.ProductRetrofitInt
import com.safronov_original_app_online_store.data.repository.ProductRepositoryIntImpl
import com.safronov_original_app_online_store.data.storage.models.converters.ProductConverter
import com.safronov_original_app_online_store.data.storage.selected_item_history.product.StorageProductApiInt
import com.safronov_original_app_online_store.data.storage.selected_item_history.product.StorageProductApiIntImpl
import com.safronov_original_app_online_store.data.storage.selected_item_history.product.dao.ProductDaoInt
import com.safronov_original_app_online_store.data.storage.sql.AppStorage
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataDi = module {

    single<ProductRepositoryInt> {
        ProductRepositoryIntImpl(networkProductApiInt = get(), storageProductApiInt = get())
    }

    single<NetworkProductApiInt> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(DummyApiBaseInfo.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        NetworkProductApiIntImpl(productRetrofitInt = retrofit.create(ProductRetrofitInt::class.java))
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