package com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.rcv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.safronov_original_app_online_store.databinding.ProductRcvItemBinding
import com.safronov_original_app_online_store.domain.model.cart.CartProduct
import com.squareup.picasso.Picasso

class RcvAllCartItems(
    private val rcvAllCartItemsInt: RcvAllCartItemsInt
): ListAdapter<CartProduct, RcvAllCartItems.AllCartItemsViewHolder>(AllCartItemsDiffUtil()) {

    class AllCartItemsViewHolder(
        val binding: ProductRcvItemBinding
    ): RecyclerView.ViewHolder(binding.root) {  }

    class AllCartItemsDiffUtil(): DiffUtil.ItemCallback<CartProduct>() {
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCartItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductRcvItemBinding.inflate(inflater, parent, false)
        return AllCartItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllCartItemsViewHolder, position: Int) {
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            Picasso.get().load(this.currentList[holder.adapterPosition].thumbnail).into(holder.binding.imgProductImg)
            val price = "${this.currentList[holder.adapterPosition].price}$"
            holder.binding.tvProductPrice.text = price
            holder.binding.tvProductName.text = this.currentList[holder.adapterPosition].title
            holder.itemView.setOnClickListener {
                if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                    rcvAllCartItemsInt.onCartItemClick(this.currentList[holder.adapterPosition])
                }
            }
        } else {
            holder.itemView.setOnClickListener(null)
        }
    }

    override fun getItemCount(): Int = this.currentList.size

    fun clearList() {
        val newList = currentList.toMutableList()
        newList.clear()
        submitList(newList.toList())
    }

}