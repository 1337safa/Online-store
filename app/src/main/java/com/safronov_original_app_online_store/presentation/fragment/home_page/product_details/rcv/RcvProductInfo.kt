package com.safronov_original_app_online_store.presentation.fragment.home_page.product_details.rcv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.safronov_original_app_online_store.databinding.ProductInfoRcvItemBinding
import com.safronov_original_app_online_store.domain.model.product.ProductInfo

class RcvProductInfo(): RecyclerView.Adapter<RcvProductInfo.ProductInfoViewHolder>() {

    private var listOfProductsInfo: List<ProductInfo> = emptyList()

    class ProductInfoViewHolder(
        val binding: ProductInfoRcvItemBinding
    ): RecyclerView.ViewHolder(binding.root) {  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductInfoRcvItemBinding.inflate(inflater)
        return ProductInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductInfoViewHolder, position: Int) {
        val item = listOfProductsInfo[holder.adapterPosition]
        holder.binding.tvSomething.text = item.title
        holder.binding.tvSomethingResult.text = item.info
    }

    override fun getItemCount(): Int {
        return listOfProductsInfo.size
    }

    fun submitList(listOfProductsInfo: List<ProductInfo>) {
        this.listOfProductsInfo = listOfProductsInfo
        notifyDataSetChanged()
    }

}