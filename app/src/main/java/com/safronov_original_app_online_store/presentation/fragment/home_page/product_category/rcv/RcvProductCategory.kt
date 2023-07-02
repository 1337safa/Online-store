package com.safronov_original_app_online_store.presentation.fragment.home_page.product_category.rcv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.safronov_original_app_online_store.databinding.ProductCategoryRcvItemBinding
import com.safronov_original_app_online_store.domain.model.product_category.SelectedProductCategory

class RcvProductCategory(
    private val rcvProductCategoryInt: RcvProductCategoryInt
): ListAdapter<String, RcvProductCategory.ProductCategoryViewHolder>(RcvProductCategory.ProductCategoryDiffUtill()) {

    private var singleSelectedItem = NO_SELECTED_ITEM
    private var defaultSelectedProduct: String? = null

    class ProductCategoryDiffUtill(): DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return newItem == oldItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return newItem == oldItem
        }
    }

    class ProductCategoryViewHolder(
        val binding: ProductCategoryRcvItemBinding
    ): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductCategoryRcvItemBinding.inflate(inflater, parent, false)
        return ProductCategoryViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ProductCategoryViewHolder, position: Int) {

        if (holder.adapterPosition != RecyclerView.NO_POSITION) {

            holder.binding.tvCategory.text = currentList[holder.adapterPosition]
            holder.binding.checkBox.isChecked = currentList[holder.adapterPosition] == defaultSelectedProduct
            holder.binding.consRoot.setOnClickListener {
                if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                    defaultSelectedProduct = currentList[holder.adapterPosition].toString()
                    rcvProductCategoryInt.onCategoryClick(currentList[holder.adapterPosition], true)
                    notifyDataSetChanged()
                }
            }

        } else {
            holder.binding.consRoot.setOnClickListener(null)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    private fun setSingleSelectedItemPosition(adapterPosition: Int) {
        if (adapterPosition == RecyclerView.NO_POSITION) {
            return
        }
        notifyItemChanged(singleSelectedItem)
        singleSelectedItem = adapterPosition
        notifyItemChanged(singleSelectedItem)
    }

    fun setSelectedProductCategory(productCategory: SelectedProductCategory) {
        defaultSelectedProduct = productCategory.productCategory
        notifyDataSetChanged()
    }

    fun clearSelectedCategory() {
        singleSelectedItem = NO_SELECTED_ITEM
        defaultSelectedProduct = null
        notifyDataSetChanged()
    }

    companion object {
        private const val NO_SELECTED_ITEM = -1
    }

}