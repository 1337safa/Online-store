package com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.rcv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.safronov_original_app_online_store.core.extensions.logD
import com.safronov_original_app_online_store.databinding.AddProductPhotoRcvItemBinding
import com.safronov_original_app_online_store.presentation.fragment.home_page.sell_product.add_product_photo.rcv.models.Photo
import com.squareup.picasso.Picasso

class RcvProductPhotos(
    private val rcvProductPhotosInt: RcvProductPhotosInt
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_TO_SHOW_IMG = 1
        const val VIEW_TYPE_TO_SHOW_TO_ADD_NEW_IMG = 2
    }

    private var listOfPhotos = emptyList<Photo>()

    class ViewHolderToShowToAddNewImg(
        val binding: AddProductPhotoRcvItemBinding
    ): ViewHolder(binding.root)

    class ViewHolderToShowImg(
        val binding: AddProductPhotoRcvItemBinding
    ): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        logD("View type is: ${viewType}, list: ${listOfPhotos}")
        return when(viewType) {
            VIEW_TYPE_TO_SHOW_TO_ADD_NEW_IMG -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = AddProductPhotoRcvItemBinding.inflate(inflater, parent, false)
                logD("SHOW TO ADD")
                return ViewHolderToShowToAddNewImg(binding = binding)
            }
            VIEW_TYPE_TO_SHOW_IMG -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = AddProductPhotoRcvItemBinding.inflate(inflater, parent, false)
                logD("SHOW TO SHOW")
                return ViewHolderToShowImg(binding = binding)
            }
            else -> {
                throw IllegalStateException("No ViewHolder found for this ViewType: $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {

            if (holder is ViewHolderToShowToAddNewImg) {
                val imgResource = listOfPhotos[holder.adapterPosition].imgResource
                if (imgResource != null) {
                    holder.binding.productImg.setImageResource(imgResource)
                    holder.binding.productImg.scaleType = ImageView.ScaleType.CENTER
                    holder.binding.deleteProductImg.visibility = View.GONE
                    holder.binding.productImg.setOnClickListener {
                        rcvProductPhotosInt.clickOnButtonToAddNewProductImg()
                    }
                } else {
                    holder.binding.productImg.setOnClickListener(null)
                    holder.binding.deleteProductImg.visibility = View.GONE
                }
            } else if (holder is ViewHolderToShowImg) {
                Picasso.get().load(listOfPhotos[holder.adapterPosition].imgUrl).into(holder.binding.productImg)
                holder.binding.deleteProductImg.visibility = View.VISIBLE
                holder.binding.productImg.setOnClickListener {
                    if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                        deleteProductPhoto(holder.adapterPosition)
                    }
                }
                logD("ViewHolder for show img")
            }

        }
    }

    override fun getItemCount(): Int {
        return listOfPhotos.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (listOfPhotos[position].viewType) {
            VIEW_TYPE_TO_SHOW_TO_ADD_NEW_IMG -> {
                logD("View type img: ${VIEW_TYPE_TO_SHOW_TO_ADD_NEW_IMG}, item: ${listOfPhotos[position].viewType}")
                VIEW_TYPE_TO_SHOW_TO_ADD_NEW_IMG
            }
            VIEW_TYPE_TO_SHOW_IMG -> {
                logD("View type img: ${VIEW_TYPE_TO_SHOW_IMG}, item: ${listOfPhotos[position].viewType}")
                VIEW_TYPE_TO_SHOW_IMG
            }
            else -> {
                throw IllegalStateException("No ViewHolder ViewType")
            }
        }
    }

    fun addPhotoToShowToAddNewPhoto(photo: Photo) {
        val mList = listOfPhotos.toMutableList()
        mList.add(0, photo)
        listOfPhotos = mList.toList()
    }

    fun addPhoto(photo: Photo) {
        val mList = listOfPhotos.toMutableList()
        mList.add(photo)
        listOfPhotos = mList.toList()
        notifyDataSetChanged()
    }

    private fun deleteProductPhoto(adapterPosition: Int) {
        val mList = listOfPhotos.toMutableList()
        mList.removeAt(adapterPosition)
        listOfPhotos = mList.toList()
        notifyDataSetChanged()
    }

}