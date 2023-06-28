package com.safronov_original_app_online_store.di

import com.safronov_original_app_online_store.domain.service.product.ProductsServiceIntImpl
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import org.koin.dsl.module

val domainDi = module {

    factory<ProductsServiceInt> {
        ProductsServiceIntImpl(productRepositoryInt = get())
    }

}