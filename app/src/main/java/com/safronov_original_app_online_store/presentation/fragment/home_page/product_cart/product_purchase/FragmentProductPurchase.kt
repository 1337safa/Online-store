package com.safronov_original_app_online_store.presentation.fragment.home_page.product_cart.product_purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.databinding.FragmentProductPurchaseBinding
import com.safronov_original_app_online_store.domain.model.bank_card.BankCard
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_cart.product_purchase.rcv.RcvBankCards
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_cart.product_purchase.rcv.RcvBankCardsInt
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_cart.product_purchase.view_model.FragmentProductPurchaseVM
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.rcv.RcvImgSlider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentProductPurchase : Fragment(), RcvBankCardsInt {

    private var _binding: FragmentProductPurchaseBinding? = null
    private val binding get() = _binding!!
    private val rcvBankCards = RcvBankCards(rcvBankCardsInt = this)
    private val rcvImgsSlider = RcvImgSlider()

    private val fragmentProductPurchaseVM by viewModel<FragmentProductPurchaseVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            fragmentProductPurchaseVM.loadCurrentProductById(productId = getArgsAsProductId().toString())
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun getArgsAsProductId(): Int? {
        return arguments?.getInt(PRODUCT_ID, DEFAULT_PRODUCT_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductPurchaseBinding.inflate(inflater, container, false)
        try {
            fragmentProductPurchaseVM.loadAllUserBankCards()
            initViewPager()
            initRcv()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
        return binding.root
    }

    private fun initRcv() {
        binding.rcvUserBankCards.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvUserBankCards.adapter = rcvBankCards
    }

    private fun initViewPager() {
        binding.viewPagerOfImgs.adapter = rcvImgsSlider
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            allUserBankCardsListener()
            currentProductListener()
            tvShowUserBankCardListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun tvShowUserBankCardListener() {
        binding.tvShowUserBankCard.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentProductPurchase_to_fragmentAddUserBankCards)
        }
    }

    private fun currentProductListener() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            fragmentProductPurchaseVM.currentProduct.collect {
                if (it != null) {
                    bindViewByDataAboutProduct(it)
                }
            }
        }
    }

    private fun bindViewByDataAboutProduct(product: Product) {
        rcvImgsSlider.submitList(product.images)
        binding.tvProductPrice.setText("${product.price}$")
        val price = getString(R.string.buy) + " - ${product.price + 100}$"
        binding.btnAddToCart.text = price
        binding.tvProductName.text = product.title
        binding.tvProductDescription.text = product.description
    }

    private fun allUserBankCardsListener() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            fragmentProductPurchaseVM.allUserBankCards.collect {
                if (it != null) {
                    rcvBankCards.submitList(it)
                }
            }
        }
    }

    override fun onBankCardClick(bankCard: BankCard) {
        //TODO write code to save user bank card, with which he will pay
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentProductPurchase()
        /**
         * Fragment [FragmentProductPurchase] takes on this [PRODUCT_ID]
         * an int that indicates the product ID to view full product information */
        const val PRODUCT_ID = "Product ID"
        private const val DEFAULT_PRODUCT_ID = -1
    }

}