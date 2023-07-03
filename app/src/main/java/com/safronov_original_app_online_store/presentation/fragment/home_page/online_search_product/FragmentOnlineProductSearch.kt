package com.safronov_original_app_online_store.presentation.fragment.home_page.online_search_product

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logD
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.core.extensions.showInputMethod
import com.safronov_original_app_online_store.databinding.FragmentOnlineProductSearchBinding
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.presentation.fragment.home_page.home_page.rcv.RcvAllProducts
import com.safronov_original_app_online_store.presentation.fragment.home_page.home_page.rcv.RcvAllProductsInt
import com.safronov_original_app_online_store.presentation.fragment.home_page.online_search_product.view_model.FragmentOnlineProductSearchVM
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.FragmentProductDetails
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class FragmentOnlineProductSearch : Fragment(), RcvAllProductsInt {

    private var _binding: FragmentOnlineProductSearchBinding? = null
    private val binding get() = _binding!!
    private val rcvAllProducts = RcvAllProducts(rcvAllProductsInt = this)
    private val fragmentOnlineProductSearchVM by viewModel<FragmentOnlineProductSearchVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnlineProductSearchBinding.inflate(inflater, container, false)
        try {
            initRcv()
            loadAllProductsBySearchText()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}")
        }
        return binding.root
    }

    private fun initRcv() {
        binding.rcvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvProducts.adapter = rcvAllProducts
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            initSearchView()
            srchSearchProductQueryTextListener()
            allProductsListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}")
        }
    }

    private fun allProductsListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            fragmentOnlineProductSearchVM.allProducts.collect {
                if (it != null) {
                    rcvAllProducts.submitList(it.products)
                }
            }
        }
    }

    private fun initSearchView() {
        binding.srchSearchProduct.requestFocus()
        binding.srchSearchProduct.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                showInputMethod(view.findFocus())
            }
        }
    }

    private fun srchSearchProductQueryTextListener() {
        binding.srchSearchProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchText: String?): Boolean {
                if (searchText != null) {
                    fragmentOnlineProductSearchVM.saveCurrentSearchText(searchText = searchText)
                    loadAllProductsBySearchText()
                }
                return true
            }

            override fun onQueryTextChange(searchText: String?): Boolean {
                if (searchText != null) {
                    fragmentOnlineProductSearchVM.saveCurrentSearchText(searchText = searchText)
                    loadAllProductsBySearchText()
                }
                return true
            }
        })
    }

    private fun loadAllProductsBySearchText() {
        val searchText = fragmentOnlineProductSearchVM.getCurrentSearchText()
        if (searchText?.isEmpty() == true) {
            rcvAllProducts.clearList()
        } else {
            fragmentOnlineProductSearchVM.loadAllProductsBySearch(searchText = searchText.toString())
        }
    }

    override fun onProductClick(product: Product) {
        try {
            findNavController().navigate(
                R.id.action_fragmentOnlineProductSearch_to_fragmentProductDetails,
                bundleOf(
                    FragmentProductDetails.PRODUCT_ID_TO_SHOW_PRODUCT_DETAILS to product.id
                )
            )
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentOnlineProductSearch()
    }

}