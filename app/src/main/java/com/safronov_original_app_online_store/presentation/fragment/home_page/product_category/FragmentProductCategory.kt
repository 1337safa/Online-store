package com.safronov_original_app_online_store.presentation.fragment.home_page.product_category

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
import com.safronov_original_app_online_store.databinding.FragmentProductCategoryBinding
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_category.rcv.RcvProductCategory
import com.safronov_original_app_online_store.presentation.fragment.home_page.product_category.view_model.FragmentProductCategoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentProductCategory : Fragment() {

    private var _binding: FragmentProductCategoryBinding? = null
    private val binding get() = _binding!!
    private val rcvProductCategory = RcvProductCategory()

    private val fragmentProductCategoryViewModel by viewModel<FragmentProductCategoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            fragmentProductCategoryViewModel.loadProductsCategories()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductCategoryBinding.inflate(inflater, container, false)
        try {
            initRcv()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
        return binding.root
    }

    private fun initRcv() {
        binding.rcvCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvCategories.adapter = rcvProductCategory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            productCategoriesListener()
            btnBackToChooseProductListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun btnBackToChooseProductListener() {
        binding.btnBackToChooseProduct.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun productCategoriesListener() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            fragmentProductCategoryViewModel.productCategories.collect { productsCategories ->
                if (productsCategories != null) {
                    rcvProductCategory.submitList(productsCategories)
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
        fun newInstance() = FragmentProductCategory()
    }

}