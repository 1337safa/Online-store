package com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.product_purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.core.extensions.toastS
import com.safronov_original_app_online_store.databinding.AskUserBinding
import com.safronov_original_app_online_store.databinding.FragmentProductPurchaseBinding
import com.safronov_original_app_online_store.databinding.ShowUserAppRestrictionsBinding
import com.safronov_original_app_online_store.domain.model.bank_card.BankCard
import com.safronov_original_app_online_store.domain.model.product.Product
import com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.product_purchase.rcv.RcvBankCards
import com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.product_purchase.rcv.RcvBankCardsInt
import com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.product_purchase.view_model.FragmentProductPurchaseVM
import com.safronov_original_app_online_store.presentation.fragment.all_content.product_details.rcv.RcvImgSlider
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
            fragmentProductPurchaseVM.savePrimaryKeyOfCartProduct(primaryKey = getArgsAsCartPrimaryKeyOfCartProduct().toString())
            fragmentProductPurchaseVM.loadCurrentProductById(productId = getArgsAsProductId().toString())
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun getArgsAsProductId(): Int? {
        return arguments?.getInt(PRODUCT_ID, DEFAULT_PRODUCT_ID)
    }

    private fun getArgsAsCartPrimaryKeyOfCartProduct(): Int? {
        return arguments?.getInt(PRIMARY_KEY_OF_CART_PRODUCT, DEFAULT_PRODUCT_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductPurchaseBinding.inflate(inflater, container, false)
        try {
            showUserThatDataIsLoading()
            fragmentProductPurchaseVM.loadAllUserBankCards()
            initViewPager()
            initRcv()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
        return binding.root
    }

    private fun showUserThatDataIsLoading() {
        binding.tvDeleteCurrentProductFromCart.visibility = View.GONE
        binding.tvProductName.visibility = View.GONE
        binding.tvProductPrice.visibility = View.GONE
        binding.tvAboutProduct.visibility = View.GONE
        binding.tvProductDescription.visibility = View.GONE
        binding.tvShowPriceForDelivery.visibility = View.GONE
        binding.tvPriceForDelivery.visibility = View.GONE
        binding.tvShowUserBankCard.visibility = View.GONE
        binding.rcvUserBankCards.visibility = View.GONE
        binding.btnBuyProduct.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun initRcv() {
        binding.rcvUserBankCards.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvUserBankCards.adapter = rcvBankCards
        val selectedBankCard = fragmentProductPurchaseVM.getSelectedBankCard()
        if (selectedBankCard != null) {
            rcvBankCards.setSelectedBankCard(selectedBankCard)
        }
    }

    private fun initViewPager() {
        binding.viewPagerOfImgs.adapter = rcvImgsSlider
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            tvDeleteCurrentProductFromCartListener()
            allUserBankCardsListener()
            currentProductListener()
            tvShowUserBankCardListener()
            btnBuyProductListener()
            mainRefreshLayoutListener()
            progressBarListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun progressBarListener() {
        binding.progressBar.setOnClickListener {
            fragmentProductPurchaseVM.loadCurrentProductById(productId = getArgsAsProductId().toString())
        }
    }

    private fun mainRefreshLayoutListener() {
        binding.mainRefreshLayout.setOnRefreshListener {
            fragmentProductPurchaseVM.loadCurrentProductById(productId = getArgsAsProductId().toString())
            binding.mainRefreshLayout.isRefreshing = false
        }
    }

    private fun btnBuyProductListener() {
        binding.btnBuyProduct.setOnClickListener {
            val currentBankCard = fragmentProductPurchaseVM.getSelectedBankCard()
            if (currentBankCard != null) {
                showBottomSheetDialogForUserThatItIsNotPossibleToBuyProduct()
            } else {
                toastS(getString(R.string.please_coose_cank_card))
            }
        }
    }

    private fun showBottomSheetDialogForUserThatItIsNotPossibleToBuyProduct() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = ShowUserAppRestrictionsBinding.inflate(layoutInflater)
        bottomSheetBinding.tvTitle.text = getString(R.string.important_alert)
        bottomSheetBinding.tvDescription.text = getString(R.string.description_of_the_alert_that_user_can_not_buy_product)
        bottomSheetBinding.btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.create()
        bottomSheetDialog.show()
    }

    private fun tvDeleteCurrentProductFromCartListener() {
        binding.tvDeleteCurrentProductFromCart.setOnClickListener {
            askUserDeleterProductFromCartOrNot(
                no = {},
                yes = {
                    val currentProduct = fragmentProductPurchaseVM.currentProduct.value
                    if (currentProduct != null) {
                        fragmentProductPurchaseVM.deleteCurrentProductFromCart(
                            currentProduct
                        )
                    }
                    findNavController().popBackStack()
                }
            )
        }
    }

    private fun askUserDeleterProductFromCartOrNot(no: () -> Unit, yes: () -> Unit) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = AskUserBinding.inflate(layoutInflater)
        bottomSheetBinding.btnNo.setOnClickListener {
            no.invoke()
            bottomSheetDialog.dismiss()
        }
        bottomSheetBinding.btnYes.setOnClickListener {
            yes.invoke()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.create()
        bottomSheetDialog.show()
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
                    showUserThatDataLoaded()
                }
            }
        }
    }

    private fun bindViewByDataAboutProduct(product: Product) {
        rcvImgsSlider.submitList(product.images)
        binding.tvProductPrice.setText("${product.price}$")
        val price = getString(R.string.buy) + " - ${product.price + 100}$"
        binding.btnBuyProduct.text = price
        binding.tvProductName.text = product.title
        binding.tvProductDescription.text = product.description
    }

    private fun showUserThatDataLoaded() {
        binding.tvDeleteCurrentProductFromCart.visibility = View.VISIBLE
        binding.tvProductName.visibility = View.VISIBLE
        binding.tvProductPrice.visibility = View.VISIBLE
        binding.tvAboutProduct.visibility = View.VISIBLE
        binding.tvProductDescription.visibility = View.VISIBLE
        binding.tvShowPriceForDelivery.visibility = View.VISIBLE
        binding.tvPriceForDelivery.visibility = View.VISIBLE
        binding.tvShowUserBankCard.visibility = View.VISIBLE
        binding.rcvUserBankCards.visibility = View.VISIBLE
        binding.btnBuyProduct.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
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
        try {
            fragmentProductPurchaseVM.saveSelectedBankCard(bankCard = bankCard)
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentProductPurchase()
        private const val DEFAULT_PRODUCT_ID = -1
        /**
         * Fragment [FragmentProductPurchase] takes on this [PRODUCT_ID]
         * an int that indicates the product ID to view full product information */
        const val PRODUCT_ID = "Product ID"
        /**
         * [PRIMARY_KEY_OF_CART_PRODUCT] is the ID of the product that is stored in the local database in the cart,
         * with it this fragment [FragmentProductPurchase] can remove this product from the local database
         * if the user wants it */
        const val PRIMARY_KEY_OF_CART_PRODUCT = "Primary key of cart product"
    }

}