package com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.view_model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FragmentAddProductPhotoVM(): ViewModel() {

    private val _productMainPhoto = MutableStateFlow<String?>(null)
    val productMainPhoto = _productMainPhoto.asStateFlow()

    private var secondaryProductPhotos: MutableList<String> = mutableListOf()

    fun getSecondaryProductPhotos() = secondaryProductPhotos

    fun addSecondaryProductPhoto(photo: String) {
        secondaryProductPhotos.add(photo)
    }

    fun saveProductMainPhoto(photo: String?) {
        _productMainPhoto.value = photo
    }

    fun saveSecondaryProductPhotos(list: List<String>) {
        secondaryProductPhotos = list.toMutableList()
    }

}