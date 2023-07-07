package com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart

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
import com.safronov_original_app_online_store.databinding.FragmentProductCartHomePageBinding
import com.safronov_original_app_online_store.domain.model.cart.CartProduct
import com.safronov_original_app_online_store.presentation.activity.communication_with_activity.bottom_nav_view.requireCommunicationWithBottomNavView
import com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.product_purchase.FragmentProductPurchase
import com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.rcv.RcvAllCartItems
import com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.rcv.RcvAllCartItemsInt
import com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.view_model.FragmentProductCartHomePageVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentProductCartHomePage : Fragment(), RcvAllCartItemsInt {

    private var _binding: FragmentProductCartHomePageBinding? = null
    private val binding get() = _binding!!
    private val rcvAllCartItems = RcvAllCartItems(rcvAllCartItemsInt = this)

    private val fragmentProductCartHomePageVM by viewModel<FragmentProductCartHomePageVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductCartHomePageBinding.inflate(inflater, container, false)
        try {
            initRcv()
            loadAllCartItems()
            requireCommunicationWithBottomNavView()?.hideBadgeForCartGraph()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
        return binding.root
    }

    private fun initRcv() {
        binding.rcvCartItems.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvCartItems.adapter = rcvAllCartItems
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            allCartItemsListener()
            srchSearchCartItemsOnQueryTextListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun srchSearchCartItemsOnQueryTextListener() {
        binding.srchSearchCartItems.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchText: String?): Boolean {
                fragmentProductCartHomePageVM.saveCurrentSearchText(searchText = searchText ?: "")
                loadAllCartItems()
                return true
            }

            override fun onQueryTextChange(searchText: String?): Boolean {
                fragmentProductCartHomePageVM.saveCurrentSearchText(searchText = searchText ?: "")
                loadAllCartItems()
                return true
            }
        })
    }

    private fun allCartItemsListener() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            fragmentProductCartHomePageVM.allCartItems.collect {
                if (it != null) {
                    rcvAllCartItems.submitList(it)
                }
            }
        }
    }

    override fun onCartItemClick(cartProduct: CartProduct) {
        try {
            if (cartProduct.productId?.isNotEmpty() == true) {
                findNavController().navigate(
                    R.id.action_fragmentProductCartHomePage_to_fragmentProductPurchase,
                    bundleOf(
                        FragmentProductPurchase.PRODUCT_ID to cartProduct.productId?.toIntOrNull(),
                        FragmentProductPurchase.PRIMARY_KEY_OF_CART_PRODUCT to cartProduct.primaryKey
                    )
                )
            }
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun loadAllCartItems() {
        val searchText = fragmentProductCartHomePageVM.getCurrentSearchText()
        if (searchText?.isNotEmpty() == true) {
            fragmentProductCartHomePageVM.loadAllCartProductEntitiesByProductTitle(productTitle = searchText)
        } else {
            fragmentProductCartHomePageVM.loadAllCartItems()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentProductCartHomePage()
    }

}