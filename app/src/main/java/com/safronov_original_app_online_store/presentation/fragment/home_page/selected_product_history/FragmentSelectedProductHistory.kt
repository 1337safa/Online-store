package com.safronov_original_app_online_store.presentation.fragment.home_page.selected_product_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.databinding.FragmentSelectedProductHistoryBinding
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.FragmentProductDetails
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.rcv.RcvSelectedProducts
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.rcv.RcvSelectedProductsInt
import com.safronov_original_app_online_store.presentation.fragment.home_page.selected_product_history.view_model.FragmentSelectedProductHistoryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentSelectedProductHistory : Fragment(), RcvSelectedProductsInt {

    private var _binding: FragmentSelectedProductHistoryBinding? = null
    private val binding get() = _binding!!
    private val rcvAllProducts = RcvSelectedProducts(rcvSelectedProductsInt = this)

    private val fragmentSelectedProductHistoryViewModel by viewModel<FragmentSelectedProductHistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectedProductHistoryBinding.inflate(inflater, container, false)
        try {
            loadAllProductsBySearchText()
            initRcv()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
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
            allSelectedProductsListener()
            srchSearchProductQueryTextListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun srchSearchProductQueryTextListener() {
        binding.srchSearchProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchText: String?): Boolean {
                fragmentSelectedProductHistoryViewModel.saveCurrentSearchText(searchText = searchText ?: "")
                loadAllProductsBySearchText()
                return true
            }

            override fun onQueryTextChange(searchText: String?): Boolean {
                fragmentSelectedProductHistoryViewModel.saveCurrentSearchText(searchText = searchText ?: "")
                loadAllProductsBySearchText()
                return true
            }
        })
    }

    private fun loadAllProductsBySearchText() {
        val searchText = fragmentSelectedProductHistoryViewModel.getCurrentSearchText()
        if (searchText?.isNotEmpty() == true) {
            fragmentSelectedProductHistoryViewModel.loadSelectedProductsByTitle(productTitle = searchText)
        } else {
            fragmentSelectedProductHistoryViewModel.loadAllSelectedProducts()
        }
    }

    private fun allSelectedProductsListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            fragmentSelectedProductHistoryViewModel.allSelectedProducts.collect {
                if (it != null) {
                    rcvAllProducts.submitList(it)
                }
            }
        }
    }

    override fun onSelectedProductClick(selectedProduct: SelectedProduct) {
        try {
            findNavController().navigate(
                R.id.action_fragmentSelectedProductHistory_to_fragmentProductDetails2,
                bundleOf(FragmentProductDetails.PRODUCT_ID_TO_SHOW_PRODUCT_DETAILS to selectedProduct.productId.toInt())
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
        fun newInstance() = FragmentSelectedProductHistory()
    }

}