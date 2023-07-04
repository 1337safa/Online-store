package com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.databinding.FragmentSellProductBinding
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.FragmentAddProductPhoto
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.models.SelectedProductPhoto
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.view_model.FragmentSellProductVM
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentSellProduct : Fragment() {

    private var _binding: FragmentSellProductBinding? = null
    private val binding get() = _binding!!

    private val fragmentSellProductVM by viewModel<FragmentSellProductVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            tvAddProductImgListener()
            fragmentResultListenerForFragmentAddProductPhoto()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun fragmentResultListenerForFragmentAddProductPhoto() {
        parentFragmentManager.setFragmentResultListener(
            FragmentAddProductPhoto.REQUEST_FOR_GET_PRODUCT_PHOTOS,
            viewLifecycleOwner,
            FragmentResultListener() { requestKey, result ->
                if (requestKey == FragmentAddProductPhoto.REQUEST_FOR_GET_PRODUCT_PHOTOS) {
                    val selectedProductPhotos = result.getSerializable(FragmentAddProductPhoto.SELECTED_PRODUCT_PHOTO) as SelectedProductPhoto?
                    fragmentSellProductVM.currentProductMainPhoto = selectedProductPhotos?.mainProductPhoto
                    fragmentSellProductVM.currentSecondaryProductPhotos = selectedProductPhotos?.secondaryProductPhotos
                }
            }
        )
    }

    private fun tvAddProductImgListener() {
        binding.tvAddProductImg.setOnClickListener {
            val selectedProductPhoto = SelectedProductPhoto(
                mainProductPhoto = fragmentSellProductVM.currentProductMainPhoto,
                secondaryProductPhotos = fragmentSellProductVM.currentSecondaryProductPhotos
            )
            findNavController().navigate(
                R.id.action_fragmentSellProduct_to_fragmentAddProductPhoto,
                bundleOf(
                    FragmentAddProductPhoto.SELECTED_PRODUCT_PHOTO to selectedProductPhoto
                )
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentSellProduct()
    }

}