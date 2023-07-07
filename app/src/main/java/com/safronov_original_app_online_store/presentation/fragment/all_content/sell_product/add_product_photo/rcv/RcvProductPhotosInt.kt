package com.safronov_original_app_online_store.presentation.fragment.all_content.sell_product.add_product_photo.rcv

import com.safronov_original_app_online_store.presentation.fragment.all_content.sell_product.add_product_photo.rcv.models.RcvSecondaryProductPhotosModel

interface RcvProductPhotosInt {

    fun clickOnItemToAddNewSecondaryProductPhoto()
    fun userDeletedSecondaryProductPhoto(newList: List<RcvSecondaryProductPhotosModel.SecondaryProductPhoto>)

}