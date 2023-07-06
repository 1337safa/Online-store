package com.safronov_original_app_online_store.di

import com.safronov_original_app_online_store.domain.service.bank_card.BankCardServiceInt
import com.safronov_original_app_online_store.domain.service.bank_card.BankCardServiceIntImpl
import com.safronov_original_app_online_store.domain.service.cart.CartServiceInt
import com.safronov_original_app_online_store.domain.service.cart.CartServiceIntImpl
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceIntImpl
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import com.safronov_original_app_online_store.domain.service.product_category.ProductCategoriesServiceInt
import com.safronov_original_app_online_store.domain.service.product_category.ProductCategoriesServiceIntImpl
import org.koin.dsl.module

val domainDi = module {

    factory<ProductsServiceInt> {
        ProductsServiceIntImpl(productRepositoryInt = get())
    }

    factory<ProductCategoriesServiceInt> {
        ProductCategoriesServiceIntImpl(productCategoryRepositoryInt = get())
    }

    factory<CartServiceInt> {
        CartServiceIntImpl(cartRepositoryInt = get())
    }

    factory<BankCardServiceInt> {
        BankCardServiceIntImpl(bankCardRepositoryInt = get())
    }

}