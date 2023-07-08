package com.safronov_original_app_online_store.presentation.fragment.all_content.user_profile.user_bank_cards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logD
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.databinding.FragmentUserBankCardsBinding
import com.safronov_original_app_online_store.presentation.fragment.all_content.user_profile.user_bank_cards.rcv.RcvBankCardsWithoutCheckBox
import com.safronov_original_app_online_store.presentation.fragment.all_content.user_profile.user_bank_cards.view_model.FragmentUserBankCardsVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentUserBankCards : Fragment() {

    private var _binding: FragmentUserBankCardsBinding? = null
    private val binding get() = _binding!!
    private val rcvBankCardsWithoutCheckBox = RcvBankCardsWithoutCheckBox()

    private val fragmentUserBankCardsVM by viewModel<FragmentUserBankCardsVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBankCardsBinding.inflate(inflater, container, false)
        try {
            fragmentUserBankCardsVM.loadAllUserBankCards()
            initRcv()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
        return binding.root
    }

    private fun initRcv() {
        binding.rcvAllUserBankCards.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvAllUserBankCards.adapter = rcvBankCardsWithoutCheckBox
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            tvAddBankCardListener()
            allUserBankCardsListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun tvAddBankCardListener() {
        binding.tvAddBankCard.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentUserBankCards_to_fragmentAddUserBankCards2)
        }
    }

    private fun allUserBankCardsListener() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            fragmentUserBankCardsVM.allUserBankCards.collect {
                logD("All cards: ${it}")
                if (it != null) {
                    rcvBankCardsWithoutCheckBox.submitList(it)
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
        fun newInstance() = FragmentUserBankCards()
    }

}