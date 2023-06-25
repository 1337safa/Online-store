package com.safronov_original_app_online_store.presentation.fragment.home_page.product_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.databinding.FragmentProductDetailsBinding
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.model.product.ProductInfo
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.rcv.RcvImgSlider
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.rcv.RcvProductInfo
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.view_model.FragmentProductDetailsVM
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentProductDetails : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val rcvProductInfo = RcvProductInfo()
    private val rcvImgSlider = RcvImgSlider()

    private val fragmentProductDetailsVM by viewModel<FragmentProductDetailsVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            saveCurrentProduct(getArgsAsProduct())
            prepareProductInfo()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
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

    private fun saveCurrentProduct(argsAsProduct: Product?) {
        fragmentProductDetailsVM.saveCurrentProduct(argsAsProduct)
    }

    private fun getArgsAsProduct(): Product? {
        return requireArguments().getSerializable(PRODUCT_TO_SHOW_PRODUCT_DETAILS) as Product?
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        try {
            initRcv()
            initViewPager()
            currentProductListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name} -> ${e.message}")
        }
        return binding.root
    }

    private fun initViewPager() {
        binding.viewPagerOfImgs.adapter = rcvImgSlider
    }

    private fun initRcv() {
        binding.rcvInfo.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvInfo.adapter = rcvProductInfo
    }

    private fun currentProductListener() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            fragmentProductDetailsVM.currentProduct.collect {
                if (it != null) {
                    bindViewByData(it)
                }
            }
        }
    }

    private fun bindViewByData(product: Product) {
        rcvImgSlider.submitList(product.images)
        val price = "${product.price}$"
        binding.tvProductPrice.text = price
        binding.tvProductName.text = product.title
        binding.tvProductDescription.text = product.description
        rcvProductInfo.submitList(fragmentProductDetailsVM.getCurrentProductInfo())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentProductDetails()
        const val PRODUCT_TO_SHOW_PRODUCT_DETAILS = "Product to show product details"
    }

}