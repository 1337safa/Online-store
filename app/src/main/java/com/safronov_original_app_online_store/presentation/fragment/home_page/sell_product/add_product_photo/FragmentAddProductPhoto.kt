package com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.databinding.FragmentAddProductPhotoBinding
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.rcv.RcvProductPhotos
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.rcv.RcvProductPhotosInt
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.rcv.models.Photo
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.view_model.FragmentAddProductPhotoVM
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentAddProductPhoto : Fragment(), RcvProductPhotosInt {

    private var _binding: FragmentAddProductPhotoBinding? = null
    private val binding get() = _binding!!
    private val rcvProductPhotos = RcvProductPhotos(this)

    private val fragmentAddProductPhotoVM by viewModel<FragmentAddProductPhotoVM>()
    private var pickProductMainImgActivityResultLauncher: ActivityResultLauncher<Intent>? = null
    private var pickSecondaryProductImgActivityResultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            fragmentAddProductPhotoVM.saveProductMainPhoto(getArgsAsProductMainPhoto())
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun getArgsAsProductMainPhoto(): String? {
        return arguments?.getString(DEFAULT_PRODUCT_MAIN_PHOTO, null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductPhotoBinding.inflate(inflater, container, false)
        try {
            initRcv()
            prepareView()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
        return binding.root
    }

    private fun initRcv() {
        binding.rcvMoreImgs.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvMoreImgs.adapter = rcvProductPhotos
        rcvProductPhotos.addPhotoToShowToAddNewPhoto(Photo(
            viewType = RcvProductPhotos.VIEW_TYPE_TO_SHOW_TO_ADD_NEW_IMG,
            imgResource = R.drawable.ic_add_photo
        ))
    }

    private fun prepareView() {
        binding.repaceProductImg.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            productImgOnClickListener()
            pickProductMainImgActivityResultLauncherListener()
            pickSecondaryProductImgActivityResultLauncherListener()
            productMainPhotoListener()
            btnSaveListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun pickSecondaryProductImgActivityResultLauncherListener() {
        pickSecondaryProductImgActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data?.data != null) {
                fragmentAddProductPhotoVM.addProductPhoto(it.data?.data.toString())
                rcvProductPhotos.addPhoto(Photo(
                    viewType = RcvProductPhotos.VIEW_TYPE_TO_SHOW_IMG,
                    imgUrl = it.data?.data.toString()
                ))
            } else {
                logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, something went wrong when getting product photo, result code: ${it.resultCode}, data: ${it.data}")
            }
        }
    }

    private fun pickProductMainImgActivityResultLauncherListener() {
        pickProductMainImgActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data?.data != null) {
                fragmentAddProductPhotoVM.saveProductMainPhoto(it.data?.data.toString())
            } else {
                logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, something went wrong when getting product main photo, result code: ${it.resultCode}, data: ${it.data}")
            }
        }
    }

    private fun btnSaveListener() {
        binding.btnSave.setOnClickListener {
            val productMainPhoto = fragmentAddProductPhotoVM.productMainPhoto.value
            if (productMainPhoto != null) {
                parentFragmentManager.setFragmentResult(REQUEST_KEY_FOR_GET_PHOTOS, bundleOf(
                    NEW_PRODUCT_MAIN_PHOTO to productMainPhoto
                ))
            } else {
                Snackbar.make(binding.root, getString(R.string.add_main_product_photo), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun productMainPhotoListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            fragmentAddProductPhotoVM.productMainPhoto.collect {
                if (it != null) {
                    binding.productImg.scaleType = ImageView.ScaleType.CENTER_CROP
                    Picasso.get().load(it).into(binding.productImg)
                    binding.repaceProductImg.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun productImgOnClickListener() {
        binding.productImg.setOnClickListener {
            launchActivityResultForPickProductMainPhoto()
        }
    }

    private fun launchActivityResultForPickProductMainPhoto() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = INTENT_TYPE_FOR_CHOOSE_IMG
        pickProductMainImgActivityResultLauncher?.launch(intent)
    }

    override fun clickOnButtonToAddNewProductImg() {
        try {
            launchActivityResultForPickSecondaryProductImg()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun launchActivityResultForPickSecondaryProductImg() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = INTENT_TYPE_FOR_CHOOSE_IMG
        pickSecondaryProductImgActivityResultLauncher?.launch(intent)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() =  FragmentAddProductPhoto()
        const val REQUEST_KEY_FOR_GET_PHOTOS = "Request key for get photos"
        const val NEW_PRODUCT_MAIN_PHOTO = "New product main photo"
        const val DEFAULT_PRODUCT_MAIN_PHOTO = "Default product main photo"
        private const val INTENT_TYPE_FOR_CHOOSE_IMG = "image/*"
    }

}