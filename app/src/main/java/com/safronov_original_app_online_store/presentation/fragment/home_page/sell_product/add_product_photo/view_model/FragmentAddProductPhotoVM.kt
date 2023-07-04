package com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.view_model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FragmentAddProductPhotoVM(): ViewModel() {

    private val _productMainPhoto = MutableStateFlow<String?>(null)
    val productMainPhoto = _productMainPhoto.asStateFlow()

    private val _productPhotos = MutableStateFlow<MutableList<String>?>(null)
    val productPhotos = _productPhotos.asStateFlow()

    fun saveProductMainPhoto(photo: String?) {
        _productMainPhoto.value = photo
    }

    fun addProductPhoto(photo: String) {
        _productPhotos.value?.add(photo) ?: emptyList<String>().toMutableList().add(photo)
    }

}