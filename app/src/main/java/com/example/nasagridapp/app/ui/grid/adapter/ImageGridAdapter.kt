package com.example.nasagridapp.app.ui.grid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.nasagridapp.R
import com.example.nasagridapp.app.model.ImageModel
import com.example.nasagridapp.databinding.ImageItemBinding

class ImageGridAdapter(private val listener: OnItemClickListener, private val imageList: ArrayList<ImageModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ImageItemBinding.inflate(layoutInflater,parent,false)
        return ImageGridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ImageGridViewHolder -> {
                Glide.with(holder.binding.root.context)
                    .load(imageList[position].url)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.nasa_placeholder)
                    .thumbnail(0.5f)
                    .into(holder.binding.wallpaperDisplay)
                holder.binding.wallpaperDisplay.setOnClickListener {
                    listener.onImageClick(position)
                }
            }
        }
    }

    override fun getItemCount(): Int = imageList.size

    interface OnItemClickListener {
        fun onImageClick(position: Int)
    }

    inner class ImageGridViewHolder(val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root)

}