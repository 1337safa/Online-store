package com.safronov_original_app_online_store.presentation.fragment.home_page.product_category.rcv

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.safronov_original_app_online_store.databinding.ProductCategoryRcvItemBinding
import com.safronov_original_app_online_store.domain.model.product.ProductCategories

class RcvProductCategory(

): ListAdapter<String, RcvProductCategory.ProductCategoryViewHolder>(RcvProductCategory.ProductCategoryDiffUtill()) {

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
    ): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductCategoryRcvItemBinding.inflate(inflater, parent, false)
        return ProductCategoryViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ProductCategoryViewHolder, position: Int) {
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            holder.binding.swtSwitch.text = currentList[holder.adapterPosition].toString()
            holder.binding.swtSwitch.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
                    Log.d("MyLog", "Is checked: ${isChecked}")
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


}