package com.safronov_original_app_online_store.di

import com.safronov_original_app_online_store.domain.service.product.imp.ProductServiceIntImpl
import com.safronov_original_app_online_store.domain.service.product.interfaces.ProductServiceInt
import org.koin.dsl.module

val domainDi = module {

    factory<ProductServiceInt> {
        ProductServiceIntImpl(productRepositoryInt = get())
    }

}