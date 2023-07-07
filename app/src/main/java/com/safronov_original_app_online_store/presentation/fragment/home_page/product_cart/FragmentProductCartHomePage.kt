package com.safronov_original_app_online_store.presentation.fragment.home_page.product_cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.databinding.FragmentProductCartHomePageBinding
import com.safronov_original_app_online_store.domain.model.cart.CartProduct
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_cart.product_purchase.FragmentProductPurchase
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_cart.rcv.RcvAllCartItems
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_cart.rcv.RcvAllCartItemsInt
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_cart.view_model.FragmentProductCartHomePageVM
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
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
        return binding.root
    }

    private fun initRcv() {
        binding.rcvCartItems.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvCartItems.adapter = rcvAllCartItems
    }

    private fun loadAllCartItems() {
        fragmentProductCartHomePageVM.loadAllCartItems()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            allCartItemsListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentProductCartHomePage()
    }

}