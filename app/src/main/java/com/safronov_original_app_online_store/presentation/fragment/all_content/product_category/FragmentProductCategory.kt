package com.safronov_original_app_online_store.presentation.fragment.all_content.product_category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.databinding.FragmentProductCategoryBinding
import com.safronov_original_app_online_store.presentation.fragment.all_content.product_category.rcv.RcvProductCategory
import com.safronov_original_app_online_store.presentation.fragment.all_content.product_category.rcv.RcvProductCategoryInt
import com.safronov_original_app_online_store.presentation.fragment.all_content.product_category.view_model.FragmentProductCategoryVM
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentProductCategory : Fragment(), RcvProductCategoryInt {

    private var _binding: FragmentProductCategoryBinding? = null
    private val binding get() = _binding!!
    private val rcvProductCategory = RcvProductCategory(rcvProductCategoryInt = this)

    private val fragmentProductCategoryVM by viewModel<FragmentProductCategoryVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductCategoryBinding.inflate(inflater, container, false)
        try {
            fragmentProductCategoryVM.loadProductsCategories()
            showUserThatDataIsLoading()
            initRcv()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
        return binding.root
    }

    private fun showUserThatDataIsLoading() {
        binding.rcvCategories.visibility = View.GONE
        binding.btnBackToChooseProduct.visibility = View.GONE
        binding.tvClearCategory.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
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
            tvClearCategoryListener()
            mainRefreshLayoutListener()
            progressBarListener()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun progressBarListener() {
        binding.progressBar.setOnClickListener {
            fragmentProductCategoryVM.loadProductsCategories()
        }
    }

    private fun mainRefreshLayoutListener() {
        binding.mainRefreshLayout.setOnRefreshListener {
            fragmentProductCategoryVM.loadProductsCategories()
            binding.mainRefreshLayout.isRefreshing = false
        }
    }

    private fun tvClearCategoryListener() {
        binding.tvClearCategory.setOnClickListener {
            fragmentProductCategoryVM.insertSelectedProductCategory("", false)
            rcvProductCategory.clearSelectedCategory()
        }
    }

    private fun btnBackToChooseProductListener() {
        binding.btnBackToChooseProduct.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun productCategoriesListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            fragmentProductCategoryVM.productCategories.collect { productsCategories ->
                if (productsCategories != null) {
                    rcvProductCategory.submitList(productsCategories)
                    fragmentProductCategoryVM.getSelectedProductCategory {
                        rcvProductCategory.setSelectedProductCategory(it)
                    }
                    showUserThatDataLoaded()
                }
            }
        }
    }

    private fun showUserThatDataLoaded() {
        binding.rcvCategories.visibility = View.VISIBLE
        binding.btnBackToChooseProduct.visibility = View.VISIBLE
        binding.tvClearCategory.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    override fun onCategoryClick(category: String, isChecked: Boolean) {
        try {
            fragmentProductCategoryVM.insertSelectedProductCategory(category = category, isChecked = isChecked)
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
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