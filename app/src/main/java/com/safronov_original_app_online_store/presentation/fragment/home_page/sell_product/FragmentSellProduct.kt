package com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.core.extensions.toastS
import com.safronov_original_app_online_store.databinding.AddedUserProductToSereverBottomSheetDialogBinding
import com.safronov_original_app_online_store.databinding.FragmentSellProductBinding
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.FragmentAddProductPhoto
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.models.SelectedProductPhoto
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.view_model.FragmentSellProductVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            btnSellUserProductListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun btnSellUserProductListener() {
        binding.btnSellUserProduct.setOnClickListener {
            val productName = binding.edtvProductName.text.toString().trim()
            val productCategory = binding.edtvProductCategory.text.toString().trim()
            val productBrand = binding.edtvProductBrand.text.toString().trim()
            val productPrice = binding.edtvProductPrice.text.toString().trim()
            val productDescription = binding.edtvProductDescription.text.toString().trim()
            val mainProductPhoto = fragmentSellProductVM.currentProductMainPhoto
            val secondaryProductPhotos = fragmentSellProductVM.currentSecondaryProductPhotos
            if (
                productName.isNotEmpty() && productCategory.isNotEmpty() &&
                        productBrand.isNotEmpty() && productPrice.isNotEmpty() &&
                        productDescription.isNotEmpty() && mainProductPhoto?.isNotEmpty() == true && secondaryProductPhotos?.isNotEmpty() == true
            ) {
                //TODO write code to save new product!
                val newProduct = Product(
                    brand = productBrand,
                    category = productCategory,
                    description = productDescription,
                    discountPercentage = DEFAULT_PRODUCT_DISCOUNT_PERCENTAGE,
                    id = DEFAULT_PRODUCT_ID,
                    images = listOf(""),
                    price = productPrice.toInt(),
                    rating = DEFAULT_PRODUCT_RATING,
                    stock = DEFAULT_PRODUCT_STOCK,
                    thumbnail = "mainProductPhoto",
                    title = productName
                )
                fragmentSellProductVM.addNewProduct(
                    newProduct = newProduct, result = {
                        if (it != null) {
                            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                                showBottomSheetDialogForUserThatItIsNotPossibleToAddNewProduct()
                            }
                        } else {
                            toastS(getString(R.string.something_went_wrong))
                        }
                    }
                )
            } else {
                if (productName.isEmpty()) {
                    binding.edtvProductName.setError(getString(R.string.write_product_name))
                }
                if (productCategory.isEmpty()) {
                    binding.edtvProductCategory.setError(getString(R.string.write_product_category))
                }
                if (productBrand.isEmpty()) {
                    binding.edtvProductBrand.setError(getString(R.string.write_product_brand))
                }
                if (productPrice.isEmpty()) {
                    binding.edtvProductPrice.setError(getString(R.string.write_product_price))
                }
                if (productDescription.isEmpty()) {
                    binding.edtvProductDescription.setError(getString(R.string.write_product_desription))
                }
                if (mainProductPhoto.isNullOrEmpty()) {
                    toastS(getString(R.string.add_main_product_photo))
                }
                if (secondaryProductPhotos.isNullOrEmpty()) {
                    toastS(getString(R.string.add_secondary_product_photo))
                }
            }
        }
    }

    private fun showBottomSheetDialogForUserThatItIsNotPossibleToAddNewProduct() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = AddedUserProductToSereverBottomSheetDialogBinding.inflate(layoutInflater)
        bottomSheetBinding.btnGoToProductCategories.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.create()
        bottomSheetDialog.show()
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
        private const val DEFAULT_PRODUCT_DISCOUNT_PERCENTAGE = 0.0
        private const val DEFAULT_PRODUCT_ID = 0
        private const val DEFAULT_PRODUCT_RATING = 0.0
        private const val DEFAULT_PRODUCT_STOCK = 0
    }

}