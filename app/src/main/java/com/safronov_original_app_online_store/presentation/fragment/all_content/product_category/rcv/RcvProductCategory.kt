package com.safronov_original_app_online_store.presentation.fragment.all_content.product_category.rcv

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

    private var selectedProduct: String? = null

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
            holder.binding.checkBox.isChecked = currentList[holder.adapterPosition] == selectedProduct
            holder.binding.consRoot.setOnClickListener {
                if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                    selectedProduct = currentList[holder.adapterPosition].toString()
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

    fun setSelectedProductCategory(productCategory: SelectedProductCategory) {
        selectedProduct = productCategory.productCategory
        notifyDataSetChanged()
    }

    fun clearSelectedCategory() {
        selectedProduct = null
        notifyDataSetChanged()
    }

}