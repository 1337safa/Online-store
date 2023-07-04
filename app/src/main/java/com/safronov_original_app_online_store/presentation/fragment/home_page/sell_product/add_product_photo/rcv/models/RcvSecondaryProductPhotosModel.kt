package com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.rcv.models

import android.view.View

/**
 * This class contains two sub-classes, these two sub-classes indicate what type of data
 * [RcvSecondaryProductPhotos] and [View] should be shown on the screen */
sealed class RcvSecondaryProductPhotosModel() {

    /**
     * [ImgToShowToAddNewSecondaryProductPhoto] - this subclass means that [RcvSecondaryProductPhotos] will
     * take information from this class to show an icon when clicked on which user can add a new photo
     * to a secondary product photo. */
    class ImgToShowToAddNewSecondaryProductPhoto(
        val imgResource: Int
    ): RcvSecondaryProductPhotosModel()

    /**
     * [SecondaryProductPhoto] - This subclass holds the URL of the secondary product image.
     * [RcvSecondaryProductPhotos] takes a link to a photo from this class and shows the user as a
     * secondary image that he added. */
    class SecondaryProductPhoto(
        val imgUrl: String
    ): RcvSecondaryProductPhotosModel()

}
