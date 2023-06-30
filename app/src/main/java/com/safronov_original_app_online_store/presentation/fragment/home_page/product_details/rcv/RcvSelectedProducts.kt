package com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.rcv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.safronov_original_app_online_store.databinding.ProductRcvItemBinding
import com.safronov_original_app_online_store.domain.model.product.SelectedProduct
import com.squareup.picasso.Picasso

class RcvSelectedProducts(
    private val rcvSelectedProductsInt: RcvSelectedProductsInt
): ListAdapter<SelectedProduct, RcvSelectedProducts.AllSelectedProductsViewHolder>(AllSelectedProductsDiffUtil()) {

    class AllSelectedProductsViewHolder(
        val binding: ProductRcvItemBinding
    ): RecyclerView.ViewHolder(binding.root) {  }

    class AllSelectedProductsDiffUtil(): DiffUtil.ItemCallback<SelectedProduct>() {
        override fun areItemsTheSame(oldItem: SelectedProduct, newItem: SelectedProduct): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: SelectedProduct, newItem: SelectedProduct): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllSelectedProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductRcvItemBinding.inflate(inflater, parent, false)
        return AllSelectedProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllSelectedProductsViewHolder, position: Int) {
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            Picasso.get().load(this.currentList[holder.adapterPosition].thumbnail).into(holder.binding.imgProductImg)
            val price = "${this.currentList[holder.adapterPosition].price}$"
            holder.binding.tvProductPrice.text = price
            holder.binding.tvProductName.text = this.currentList[holder.adapterPosition].title
            holder.itemView.setOnClickListener {
                rcvSelectedProductsInt.onSelectedProductClick(this.currentList[holder.adapterPosition])
            }
        } else {
            holder.itemView.setOnClickListener(null)
        }
    }

    override fun getItemCount(): Int = this.currentList.size

}