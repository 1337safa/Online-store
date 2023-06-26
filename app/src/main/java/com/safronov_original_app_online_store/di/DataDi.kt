package com.safronov_original_app_online_store.di

import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiInt
import com.safronov_original_app_online_store.data.network.dummy_api.product.NetworkProductApiIntImpl
import com.safronov_original_app_online_store.data.network.dummy_api.product.retrofit.ProductRetrofit
import com.safronov_original_app_online_store.data.repository.ProductRepositoryIntImpl
import com.safronov_original_app_online_store.domain.repository.ProductRepositoryInt
import org.koin.dsl.module

val dataDi = module {

    single<ProductRepositoryInt> {
        ProductRepositoryIntImpl(networkProductApiInt = get())
    }

    single<NetworkProductApiInt> {
        NetworkProductApiIntImpl(retrofitProduct = get())
    }

    single<ProductRetrofit> {
        ProductRetrofit()
    }

}