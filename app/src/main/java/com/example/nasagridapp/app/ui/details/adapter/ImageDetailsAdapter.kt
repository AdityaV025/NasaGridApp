package com.example.nasagridapp.app.ui.details.adapter

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
import com.example.nasagridapp.databinding.ImageDetailItemBinding

class ImageDetailsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var imageList: ArrayList<ImageModel>
    private lateinit var listener: OnItemClickListener

    fun addImageList(imageList: ArrayList<ImageModel>) {
        this.imageList = imageList
    }

    fun setupItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ImageDetailItemBinding.inflate(layoutInflater, parent, false)
        return ImageDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageDetailViewHolder -> {
                holder.binding.progressBar.visibility = View.VISIBLE
                holder.binding.imageTitle.text = imageList[position].title
                holder.binding.imageDetails.text = imageList[position].explanation
                if (imageList[position].copyright != null)
                    holder.binding.authorName.text = "By " + imageList[position].copyright
                val requestOptions = RequestOptions().placeholder(R.drawable.nasa_placeholder)
                    .fallback(R.drawable.nasa_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

                Glide.with(holder.binding.root.context)
                    .load(imageList[position].hdurl)
                    .apply(requestOptions)
                    .listener(object : RequestListener<Drawable> {
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
                    .into(holder.binding.fullImage)
                holder.binding.viewMoreText.setOnClickListener { listener.onViewMoreClick(position) }
            }
        }
    }

    override fun getItemCount(): Int = imageList.size

    interface OnItemClickListener {
        fun onViewMoreClick(itemPosition: Int)
    }

    inner class ImageDetailViewHolder(val binding: ImageDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}