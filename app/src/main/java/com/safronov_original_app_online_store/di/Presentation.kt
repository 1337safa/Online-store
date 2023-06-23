package com.safronov_original_app_online_store.di

import com.safronov_original_app_online_store.presentation.fragment.home_page.home_page.view_model.FragmentHomePageVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationDi = module {

    viewModel<FragmentHomePageVM> {
        FragmentHomePageVM(
            productsServiceInt = get()
        )
    }

}