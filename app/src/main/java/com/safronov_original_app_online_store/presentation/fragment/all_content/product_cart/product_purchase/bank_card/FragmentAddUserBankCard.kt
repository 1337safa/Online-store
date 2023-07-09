package com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.product_purchase.bank_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logD
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.core.extensions.toastS
import com.safronov_original_app_online_store.databinding.FragmentAddUserBankCardBinding
import com.safronov_original_app_online_store.domain.model.bank_card.BankCard
import com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.product_purchase.bank_card.view_model.FragmentAddUserBankCardVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentAddUserBankCard : Fragment() {

    private var _binding: FragmentAddUserBankCardBinding? = null
    private val binding get() = _binding!!

    private val fragmentAddUserBankCardVM by viewModel<FragmentAddUserBankCardVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddUserBankCardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            btnAddBankCardListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun btnAddBankCardListener() {
        binding.btnAddBankCard.setOnClickListener {
            val bankCardNumber = binding.edtvCardNumber.text.toString().trim()
            val bankCardValidity = binding.edtvCardValidity.text.toString().trim()
            val bankCardCVC = binding.edtvCardCVC.text.toString().trim()
            if (bankCardNumber.isNotEmpty() && bankCardValidity.isNotEmpty() && bankCardCVC.isNotEmpty()) {
                val newUserBankCard = BankCard(
                    cardNumber = bankCardNumber,
                    validity = bankCardValidity,
                    CVC = bankCardCVC
                )
                fragmentAddUserBankCardVM.insertUserBankCard(bankCard = newUserBankCard, bankCardWithTheSameCardNumberExists = {
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                        binding.edtvCardNumber.setError(getString(R.string.bank_card_with_the_same_card_number_exists))
                    }
                }, added = {
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                        toastS(getString(R.string.added_bank_card))
                    }
                })
            } else {
                if (bankCardNumber.isEmpty()) {
                    binding.edtvCardNumber.setError(getString(R.string.write_bank_card_number))
                }
                if (bankCardValidity.isEmpty()) {
                    binding.edtvCardValidity.setError(getString(R.string.write_bank_card_validity))
                }
                if (bankCardCVC.isEmpty()) {
                    binding.edtvCardCVC.setError(getString(R.string.write_CVC))
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentAddUserBankCard()
    }

}