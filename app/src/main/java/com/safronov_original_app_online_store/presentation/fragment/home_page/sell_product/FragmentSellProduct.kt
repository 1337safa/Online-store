package com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.databinding.FragmentSellProductBinding

class FragmentSellProduct : Fragment() {

    private var _binding: FragmentSellProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentSellProduct()
    }

}