package com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.core.extensions.snackS
import com.safronov_original_app_online_store.databinding.FragmentAddProductPhotoBinding
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.models.SelectedProductPhoto
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.rcv.RcvSecondaryProductPhotos
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.rcv.RcvProductPhotosInt
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.rcv.models.RcvSecondaryProductPhotosModel
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.view_model.FragmentAddProductPhotoVM
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentAddProductPhoto : Fragment(), RcvProductPhotosInt {

    private var _binding: FragmentAddProductPhotoBinding? = null
    private val binding get() = _binding!!
    private val rcvSecondaryProductPhotos = RcvSecondaryProductPhotos(this)

    private val fragmentAddProductPhotoVM by viewModel<FragmentAddProductPhotoVM>()
    private var pickProductMainImgActivityResultLauncher: ActivityResultLauncher<Intent>? = null
    private var pickSecondaryProductImgActivityResultLauncher: ActivityResultLauncher<Intent>? =
        null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val selectedProductPhoto = getArgsSelectedAsProductPhoto()
            if (selectedProductPhoto != null) {
                if (selectedProductPhoto.mainProductPhoto != null) {
                    fragmentAddProductPhotoVM.saveProductMainPhoto(selectedProductPhoto.mainProductPhoto)
                }
                if (selectedProductPhoto.secondaryProductPhotos != null) {
                    fragmentAddProductPhotoVM.saveSecondaryProductPhotos(
                        selectedProductPhoto.secondaryProductPhotos ?: emptyList()
                    )
                }
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun getArgsSelectedAsProductPhoto(): SelectedProductPhoto? {
        return arguments?.getSerializable(SELECTED_PRODUCT_PHOTO) as SelectedProductPhoto?
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
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
        return binding.root
    }

    private fun initRcv() {
        binding.rcvMoreImgs.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvMoreImgs.adapter = rcvSecondaryProductPhotos
        rcvSecondaryProductPhotos.addPhotoToShowToAddNewPhoto(
            RcvSecondaryProductPhotosModel.ImgToShowToAddNewSecondaryProductPhoto(
                imgResource = R.drawable.ic_add_photo
            )
        )
        val allSecondaryProductPhotos = fragmentAddProductPhotoVM.getSecondaryProductPhotos()
        if (allSecondaryProductPhotos.isNotEmpty()) {
            val mListOfRcvSecondaryProductPhotosModel =
                mutableListOf<RcvSecondaryProductPhotosModel.SecondaryProductPhoto>()
            allSecondaryProductPhotos.forEach {
                mListOfRcvSecondaryProductPhotosModel.add(
                    RcvSecondaryProductPhotosModel.SecondaryProductPhoto(
                        imgUrl = it
                    )
                )
            }
            rcvSecondaryProductPhotos.addListOfSecondaryProductPhotos(
                mListOfRcvSecondaryProductPhotosModel.toList()
            )
        }
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
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun pickSecondaryProductImgActivityResultLauncherListener() {
        pickSecondaryProductImgActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK && it.data?.data != null) {
                    rcvSecondaryProductPhotos.addSecondaryProductPhoto(
                        rcvSecondaryProductPhotosModel = RcvSecondaryProductPhotosModel.SecondaryProductPhoto(
                            imgUrl = it.data?.data.toString()
                        )
                    )
                } else {
                    logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, something went wrong when getting product photo, result code: ${it.resultCode}, data: ${it.data}")
                }
            }
    }

    private fun pickProductMainImgActivityResultLauncherListener() {
        pickProductMainImgActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK && it.data?.data != null) {
                    fragmentAddProductPhotoVM.saveProductMainPhoto(it.data?.data.toString())
                } else {
                    logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, something went wrong when getting product main photo, result code: ${it.resultCode}, data: ${it.data}")
                }
            }
    }

    private fun btnSaveListener() {
        binding.btnSave.setOnClickListener {
            val productMainPhoto = fragmentAddProductPhotoVM.productMainPhoto.value
            val productSecondaryPhotos = getListOfPhotosAsStringsBySecondaryProductPhotos(rcvSecondaryProductPhotos.getListOfSecondaryProductPhotos())
            if (productMainPhoto != null && productSecondaryPhotos.isNotEmpty()) {
                val selectedProductPhoto = SelectedProductPhoto(
                    mainProductPhoto = productMainPhoto.toString(),
                    secondaryProductPhotos = productSecondaryPhotos
                )
                parentFragmentManager.setFragmentResult(
                    REQUEST_FOR_GET_PRODUCT_PHOTOS, bundleOf(
                        SELECTED_PRODUCT_PHOTO to selectedProductPhoto
                    )
                )
                snackS(msg = getString(R.string.added_product_photos), binding.root)
            } else {
                if (productMainPhoto?.isEmpty() == true) {
                    snackS(msg = getString(R.string.add_main_product_photo), binding.root)
                }
                if (productSecondaryPhotos.isEmpty()) {
                    snackS(msg = getString(R.string.add_secondary_product_photo), binding.root)
                }
            }
        }
    }

    private fun getListOfPhotosAsStringsBySecondaryProductPhotos(listOfSecondaryProductPhotos: List<RcvSecondaryProductPhotosModel.SecondaryProductPhoto>): List<String> {
        val mListOfPhotos = mutableListOf<String>()
        listOfSecondaryProductPhotos.forEach {
            mListOfPhotos.add(it.imgUrl)
        }
        return mListOfPhotos.toList()
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

    override fun clickOnItemToAddNewSecondaryProductPhoto() {
        try {
            launchActivityResultForPickSecondaryProductImg()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
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
        fun newInstance() = FragmentAddProductPhoto()
        const val REQUEST_FOR_GET_PRODUCT_PHOTOS = "Request for get product photo"
        const val SELECTED_PRODUCT_PHOTO = "Selected product photos"
        private const val INTENT_TYPE_FOR_CHOOSE_IMG = "image/*"
    }

}