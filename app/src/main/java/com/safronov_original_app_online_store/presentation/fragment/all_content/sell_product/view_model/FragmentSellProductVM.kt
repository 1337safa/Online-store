package com.safronov_original_app_online_store.presentation.fragment.all_content.sell_product.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.service.product.ProductsServiceInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentSellProductVM(): ViewModel() {

    var currentProductMainPhoto: String? = null
    var currentSecondaryProductPhotos: List<String>? = null

}