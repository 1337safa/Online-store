package com.safronov_original_app_online_store.presentation.fragment.home_page.product_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.databinding.FragmentProductDetailsBinding
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.model.product.ProductInfo
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.rcv.RcvImgSlider
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.rcv.RcvProductInfo
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.rcv.RcvSelectedProducts
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.rcv.RcvSelectedProductsInt
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.view_model.FragmentProductDetailsVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentProductDetails : Fragment(), RcvSelectedProductsInt {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val rcvProductInfo = RcvProductInfo()
    private val rcvImgSlider = RcvImgSlider()
    private val rcvAllProducts = RcvSelectedProducts(rcvSelectedProductsInt = this)

    private val fragmentProductDetailsVM by viewModel<FragmentProductDetailsVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        try {
            fragmentProductDetailsVM.loadCurrentProductById(getArgsAsProduct().toString())
            prepareProductInfo()
            loadAllSelectedProducts()
            initRcv()
            initViewPager()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name} -> ${e.message}")
        }
        return binding.root
    }

    private fun getArgsAsProduct(): Int {
        return requireArguments().getInt(PRODUCT_ID_TO_SHOW_PRODUCT_DETAILS, DEFAULT_PRODUCT_ID)
    }

    private fun loadAllSelectedProducts() {
        fragmentProductDetailsVM.loadAllSelectedProducts()
    }

    private fun prepareProductInfo() {
        val mutableListOfProductInfo = mutableListOf<ProductInfo>()
        val currentProduct = fragmentProductDetailsVM.currentProduct.value
        if (currentProduct != null) {
            mutableListOfProductInfo.add(
                ProductInfo(
                    title = getString(R.string.category),
                    info = currentProduct.category
                )
            )
            mutableListOfProductInfo.add(
                ProductInfo(
                    title = getString(R.string.brand),
                    info = currentProduct.brand
                )
            )
            mutableListOfProductInfo.add(
                ProductInfo(
                    title = getString(R.string.rating),
                    info = currentProduct.rating.toString()
                )
            )
            fragmentProductDetailsVM.saveCurrentProductInfo(mutableListOfProductInfo.toList())
        }
    }


    private fun initViewPager() {
        binding.viewPagerOfImgs.adapter = rcvImgSlider
    }

    private fun initRcv() {
        binding.rcvInfo.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvInfo.adapter = rcvProductInfo
        binding.rcvRecentlyViewedProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvRecentlyViewedProducts.adapter = rcvAllProducts
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            currentProductListener()
            allSelectedProductListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun allSelectedProductListener() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            fragmentProductDetailsVM.allSelectedProduct.collect { listOfSelectedProduct ->
                if (listOfSelectedProduct != null) {
                    rcvAllProducts.submitList(listOfSelectedProduct)
                }
            }
        }
    }

    private fun currentProductListener() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            fragmentProductDetailsVM.currentProduct.collect {
                if (it != null) {
                    bindViewByDataAboutProduct(it)
                }
            }
        }
    }

    private fun bindViewByDataAboutProduct(product: Product) {
        rcvImgSlider.submitList(product.images)
        val price = "${product.price}$"
        binding.tvProductPrice.text = price
        binding.tvProductName.text = product.title
        binding.tvProductDescription.text = product.description
        rcvProductInfo.submitList(fragmentProductDetailsVM.getCurrentProductInfo())
    }

    override fun onSelectedProductClick(selectedProduct: SelectedProduct) {
        try {
            findNavController().navigate(
                R.id.fragmentProductDetails,
                bundleOf(
                    PRODUCT_ID_TO_SHOW_PRODUCT_DETAILS to selectedProduct.productId.toInt()
                )
            )
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentProductDetails()
        /**
         * Fragment [FragmentProductDetails] takes on this [PRODUCT_ID_TO_SHOW_PRODUCT_DETAILS]
         * an int that indicates the product ID to view full product information */
        const val PRODUCT_ID_TO_SHOW_PRODUCT_DETAILS = "Product ID to show product details"
        private const val DEFAULT_PRODUCT_ID = -1
    }

}