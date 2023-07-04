package com.safronov_original_app_online_store.di

import com.safronov_original_app_online_store.presentation.fragment.home_page.home_page.view_model.FragmentHomePageVM
import com.safronov_original_app_online_store.presentation.fragment.home_page.online_search_product.view_model.FragmentOnlineProductSearchVM
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_category.view_model.FragmentProductCategoryVM
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.view_model.FragmentProductDetailsVM
import com.safronov_original_app_online_store.presentation.fragment.home_page.selected_product_history.view_model.FragmentSelectedProductHistoryViewModel
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.view_model.FragmentAddProductPhotoVM
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.view_model.FragmentSellProductVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationDi = module {

    viewModel<FragmentHomePageVM> {
        FragmentHomePageVM(
            productsServiceInt = get(),
            productConverter = get(),
            productCategoriesServiceInt = get()
        )
    }
    
    viewModel<FragmentProductDetailsVM> {
        FragmentProductDetailsVM(
            productsServiceInt = get()
        )
    }

    viewModel<FragmentProductCategoryVM> {
        FragmentProductCategoryVM(
            productsServiceInt = get(),
            productCategoriesServiceInt = get()
        )
    }

    viewModel<FragmentOnlineProductSearchVM> {
        FragmentOnlineProductSearchVM(
            productsServiceInt = get(),
            productConverter = get()
        )
    }

    viewModel<FragmentSelectedProductHistoryViewModel> {
        FragmentSelectedProductHistoryViewModel(
            productsServiceInt = get()
        )
    }

    viewModel<FragmentSellProductVM> {
        FragmentSellProductVM(
            productsServiceInt = get()
        )
    }

    viewModel<FragmentAddProductPhotoVM> {
        FragmentAddProductPhotoVM()
    }

}