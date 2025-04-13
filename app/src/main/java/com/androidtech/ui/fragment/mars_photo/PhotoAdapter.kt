package com.androidtech.ui.fragment.mars_photo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.androidtech.base.R
import com.androidtech.base.databinding.ItemPhotoBinding
import com.androidtech.domain.model.photo.PhotoObject

class PhotoAdapter: RecyclerView.Adapter<PhotoAdapter.ItemPhotoViewHolder>() {
    private var listPhotos = listOf<PhotoObject>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<PhotoObject>) {
        listPhotos = list
        notifyDataSetChanged()
    }

    inner class ItemPhotoViewHolder(var binding: ItemPhotoBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(photo: PhotoObject) {
                binding.itemPhoto.load(photo.imgSrc) {
                    crossfade(true)
                    placeholder(R.drawable.loading)
                }

                binding.tvId.text = photo.id
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPhotoViewHolder {
        return ItemPhotoViewHolder(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount() = listPhotos.size

    override fun onBindViewHolder(holder: ItemPhotoViewHolder, position: Int) {
        holder.bind(listPhotos[position])
    }

}