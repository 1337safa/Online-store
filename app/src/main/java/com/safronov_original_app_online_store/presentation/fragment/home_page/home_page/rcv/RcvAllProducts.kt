package com.safronov_original_app_online_store.presentation.fragment.home_page.home_page.rcv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.safronov_original_app_online_store.databinding.ProductRcvItemBinding
import com.safronov_original_app_online_store.domain.model.product.Product
import com.squareup.picasso.Picasso

class RcvAllProducts(
    private val rcvAllProductsInt: RcvAllProductsInt
): ListAdapter<Product, RcvAllProducts.AllProductsViewHolder>(AllProductsDiffUtil()) {

    class AllProductsViewHolder(
        val binding: ProductRcvItemBinding
    ): RecyclerView.ViewHolder(binding.root) {  }

    class AllProductsDiffUtil(): DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductRcvItemBinding.inflate(inflater, parent, false)
        return AllProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllProductsViewHolder, position: Int) {
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            Picasso.get().load(this.currentList[holder.adapterPosition].thumbnail).into(holder.binding.imgProductImg)
            val price = "${this.currentList[holder.adapterPosition].price}$"
            holder.binding.tvProductPrice.text = price
            holder.binding.tvProductName.text = this.currentList[holder.adapterPosition].title
            holder.itemView.setOnClickListener {
                rcvAllProductsInt.onProductClick(this.currentList[holder.adapterPosition])
            }
        } else {
            holder.itemView.setOnClickListener(null)
        }
    }

    override fun getItemCount(): Int = this.currentList.size

}