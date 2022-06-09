package com.example.nasagridapp.app.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.nasagridapp.R
import com.example.nasagridapp.app.model.ImageModel
import com.example.nasagridapp.databinding.ImageDetailItemBinding

class ImageDetailsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var imageList: ArrayList<ImageModel>

    fun addImageList(imageList: ArrayList<ImageModel>) {
        this.imageList = imageList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ImageDetailItemBinding.inflate(layoutInflater, parent, false)
        return ImageDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageDetailViewHolder -> {
                holder.binding.imageTitle.text = imageList[position].title
                holder.binding.imageDetails.text = imageList[position].explanation
                if (imageList[position].copyright != null)
                    holder.binding.authorName.text = "By " + imageList[position].copyright

                Glide.with(holder.binding.root.context)
                    .load(imageList[position].hdurl)
                    .placeholder(R.drawable.nasa_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(holder.binding.fullImage)
            }
        }
    }

    override fun getItemCount(): Int = imageList.size

    inner class ImageDetailViewHolder(val binding: ImageDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}