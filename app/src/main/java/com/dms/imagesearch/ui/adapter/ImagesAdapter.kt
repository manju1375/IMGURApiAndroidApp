package com.dms.imagesearch.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load

import com.dms.imagesearch.R
import com.dms.imagesearch.core.storage.entity.ImageDbItem
import com.dms.imagesearch.core.utils.inflate


import kotlinx.android.synthetic.main.row_image_listitem.view.*


/**
 * The Images adapter to show the news in a list.
 */
class ImagesAdapter(
    private val listener: (ImagesAdapterEvent) -> Unit
) : ListAdapter<ImageDbItem, ImagesAdapter.ImageViewHolder>(DIFF_CALLBACK) {

    /**
     * Inflate the view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(parent.inflate(R.layout.row_image_listitem))

    /**
     * Bind the view with the data
     */
    override fun onBindViewHolder(imageViewHolder: ImageViewHolder, position: Int) =
        imageViewHolder.bind(getItem(position), listener)

    /**
     * View Holder Pattern
     */
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * Binds the UI with the data and handles clicks
         */
        fun bind(imageDbItem: ImageDbItem, listener: (ImagesAdapterEvent) -> Unit) =
            with(itemView) {
                val visbility = imageDbItem?.link?.isEmpty()

                if (!visbility!!) {
                    imageViewLayout.visibility = View.VISIBLE
                    ivItem.load(imageDbItem?.link) {
                        placeholder(R.drawable.ic_launcher_foreground)
                        error(R.drawable.ic_launcher_background)
                    }
                } else {
                    imageViewLayout.visibility = View.GONE
                }
                setOnClickListener { listener(ImagesAdapterEvent.ClickEvent) }
            }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageDbItem>() {
            override fun areItemsTheSame(oldItem: ImageDbItem, newItem: ImageDbItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ImageDbItem, newItem: ImageDbItem): Boolean =
                oldItem == newItem
        }
    }
}