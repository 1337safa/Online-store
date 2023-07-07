package com.safronov_original_app_online_store.presentation.fragment.all_content.product_details.rcv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.safronov_original_app_online_store.databinding.ProductImgSliderItemBinding
import com.squareup.picasso.Picasso

class RcvImgSlider(): RecyclerView.Adapter<RcvImgSlider.SliderViewHolder>() {

    private var listOfProductImageUrls: List<String> = emptyList()

    class SliderViewHolder(
        val binding: ProductImgSliderItemBinding
    ): RecyclerView.ViewHolder(binding.root) {  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductImgSliderItemBinding.inflate(inflater, parent, false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        Picasso.get().load(listOfProductImageUrls[holder.adapterPosition]).into(holder.binding.productImg)
        val count = "${holder.adapterPosition + 1}-${listOfProductImageUrls.size}"
        holder.binding.tvCountOfImgs.text = count
    }

    override fun getItemCount(): Int {
        return listOfProductImageUrls.size
    }

    fun submitList(images: List<String>) {
        listOfProductImageUrls = images
        notifyDataSetChanged()
    }

}