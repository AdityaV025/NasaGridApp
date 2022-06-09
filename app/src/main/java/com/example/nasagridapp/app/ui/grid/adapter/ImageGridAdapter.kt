package com.example.nasagridapp.app.ui.grid.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
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
                holder.binding.progressBar.visibility = View.VISIBLE
                val requestOptions = RequestOptions().placeholder(R.drawable.nasa_placeholder)
                    .fallback(R.drawable.nasa_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

                Glide.with(holder.binding.root.context)
                    .load(imageList[position].url)
                    .apply(requestOptions)
                    .thumbnail(0.5f)
                    .listener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            holder.binding.progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            holder.binding.progressBar.visibility = View.GONE
                            return false
                        }

                    })
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