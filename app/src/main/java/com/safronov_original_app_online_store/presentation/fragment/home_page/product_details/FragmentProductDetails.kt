package com.safronov_original_app_online_store.presentation.fragment.home_page.product_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.databinding.FragmentProductDetailsBinding
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.domain.model.product.ProductInfo
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
        val product = fragmentProductDetailsVM.currentProduct.value
        if (product != null) {
            val category = ProductInfo(
                title = product::category.name,
                info = product.category
            )
            val brand = ProductInfo(
                title = product::brand.name,
                info = product.brand
            )
            val rating = ProductInfo(
                title = product::rating.name,
                info = product.rating.toString()
            )
            mutableListOfProductInfo.add(category)
            mutableListOfProductInfo.add(brand)
            mutableListOfProductInfo.add(rating)
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
            currentProductListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name} -> ${e.message}")
        }
        return binding.root
    }

    private fun initRcv() {
        binding.rcvProductInfo.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvProductInfo.adapter = rcvProductInfo
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
        Picasso.get().load(product.images.first()).into(binding.imgProductImg)
        val price = "${product.price}$"
        binding.tvProductPrice.text = price
        binding.tvProductName.text = product.title
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