package com.safronov_original_app_online_store.presentation.fragment.all_content.sell_product.add_product_photo.rcv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.safronov_original_app_online_store.databinding.AddProductPhotoRcvItemBinding
import com.safronov_original_app_online_store.presentation.exception.PresentationException
import com.safronov_original_app_online_store.presentation.fragment.all_content.sell_product.add_product_photo.rcv.models.RcvSecondaryProductPhotosModel
import com.squareup.picasso.Picasso

class RcvSecondaryProductPhotos(
    private val rcvProductPhotosInt: RcvProductPhotosInt
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listOfRcvSecondaryProductPhotosModel = emptyList<RcvSecondaryProductPhotosModel>()

    class ViewHolderToShowToAddNewImg(
        val binding: AddProductPhotoRcvItemBinding
    ) : ViewHolder(binding.root)

    class ViewHolderToShowImg(
        val binding: AddProductPhotoRcvItemBinding
    ) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_FOR_ADD_NEW_SECONDARY_PRODUCT_IMG -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = AddProductPhotoRcvItemBinding.inflate(inflater, parent, false)
                return ViewHolderToShowToAddNewImg(binding = binding)
            }
            VIEW_TYPE_FOR_SHOW_SECONDARY_PRODUCT_IMG -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = AddProductPhotoRcvItemBinding.inflate(inflater, parent, false)
                return ViewHolderToShowImg(binding = binding)
            }
            else -> {
                throw PresentationException("No ViewHolder found for this ViewType: $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {

            if (holder is ViewHolderToShowToAddNewImg) {

                val imgToShowToAddNewSecondaryProductPhoto: RcvSecondaryProductPhotosModel.ImgToShowToAddNewSecondaryProductPhoto =
                    listOfRcvSecondaryProductPhotosModel[holder.adapterPosition] as RcvSecondaryProductPhotosModel.ImgToShowToAddNewSecondaryProductPhoto

                holder.binding.productImg.setImageResource(imgToShowToAddNewSecondaryProductPhoto.imgResource)
                holder.binding.productImg.scaleType = ImageView.ScaleType.CENTER
                holder.binding.deleteProductImg.visibility = View.GONE
                holder.binding.productImg.setOnClickListener {
                    rcvProductPhotosInt.clickOnItemToAddNewSecondaryProductPhoto()
                }

            } else if (holder is ViewHolderToShowImg) {

                val secondaryProductPhotos: RcvSecondaryProductPhotosModel.SecondaryProductPhoto =
                    listOfRcvSecondaryProductPhotosModel[holder.adapterPosition] as RcvSecondaryProductPhotosModel.SecondaryProductPhoto
                Picasso.get().load(secondaryProductPhotos.imgUrl).into(holder.binding.productImg)
                holder.binding.deleteProductImg.visibility = View.VISIBLE
                holder.binding.productImg.scaleType = ImageView.ScaleType.CENTER_CROP
                holder.binding.deleteProductImg.setOnClickListener {
                    if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                        deleteSecondaryProductPhoto(holder.adapterPosition)
                    }
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return listOfRcvSecondaryProductPhotosModel.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (listOfRcvSecondaryProductPhotosModel[position]) {
            is RcvSecondaryProductPhotosModel.ImgToShowToAddNewSecondaryProductPhoto -> {
                VIEW_TYPE_FOR_ADD_NEW_SECONDARY_PRODUCT_IMG
            }
            is RcvSecondaryProductPhotosModel.SecondaryProductPhoto -> {
                VIEW_TYPE_FOR_SHOW_SECONDARY_PRODUCT_IMG
            }
        }
    }

    /**
     * This method adds an image to the first cell [listOfRcvSecondaryProductPhotosModel], this
     * image will be shown to the user as an image that they can click on and can add a
     * secondary photo for the product */
    fun addPhotoToShowToAddNewPhoto(rcvSecondaryProductPhotosModel: RcvSecondaryProductPhotosModel.ImgToShowToAddNewSecondaryProductPhoto) {
        val mList = listOfRcvSecondaryProductPhotosModel.toMutableList()
        mList.add(0, rcvSecondaryProductPhotosModel)
        listOfRcvSecondaryProductPhotosModel = mList.toList()
    }

    fun addSecondaryProductPhoto(rcvSecondaryProductPhotosModel: RcvSecondaryProductPhotosModel.SecondaryProductPhoto) {
        val mList = listOfRcvSecondaryProductPhotosModel.toMutableList()
        mList.add(rcvSecondaryProductPhotosModel)
        listOfRcvSecondaryProductPhotosModel = mList.toList()
        notifyDataSetChanged()
    }

    /**
     * This method adds a list of secondary product photos to the existing ones. */
    fun addListOfSecondaryProductPhotos(list: List<RcvSecondaryProductPhotosModel.SecondaryProductPhoto>) {
        val mList = listOfRcvSecondaryProductPhotosModel.toMutableList()
        mList.addAll(list)
        listOfRcvSecondaryProductPhotosModel = mList.toList()
        notifyDataSetChanged()
    }

    fun getListOfSecondaryProductPhotos(): List<RcvSecondaryProductPhotosModel.SecondaryProductPhoto> {
        return findListOfSecondaryProductPhoto(listOfRcvSecondaryProductPhotosModel)
    }

    private fun deleteSecondaryProductPhoto(adapterPosition: Int) {
        val mList = listOfRcvSecondaryProductPhotosModel.toMutableList()
        mList.removeAt(adapterPosition)
        listOfRcvSecondaryProductPhotosModel = mList.toList()
        rcvProductPhotosInt.userDeletedSecondaryProductPhoto(findListOfSecondaryProductPhoto(listOfRcvSecondaryProductPhotosModel))
        notifyDataSetChanged()
    }

    private fun findListOfSecondaryProductPhoto(
        list: List<RcvSecondaryProductPhotosModel>
    ): List<RcvSecondaryProductPhotosModel.SecondaryProductPhoto> {
        val mList = mutableListOf<RcvSecondaryProductPhotosModel.SecondaryProductPhoto>()
        list.forEach {
            if (it is RcvSecondaryProductPhotosModel.SecondaryProductPhoto) {
                mList.add(it)
            }
        }
        return mList.toList()
    }

    companion object {
        private const val VIEW_TYPE_FOR_SHOW_SECONDARY_PRODUCT_IMG = 1
        private const val VIEW_TYPE_FOR_ADD_NEW_SECONDARY_PRODUCT_IMG = 2
    }

}