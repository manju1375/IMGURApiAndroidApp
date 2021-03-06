package com.dms.imagesearch.ui.adapter


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.dms.imagesearch.R
import com.dms.imagesearch.api.response.Image
import com.dms.imagesearch.core.utils.RecyclerViewClickListener
import com.dms.imagesearch.core.utils.inflate
import kotlinx.android.synthetic.main.row_image_listitem.view.*


/**
 * The Images adapter to show the images in a list.
 */
class ImagesListAdapter(
    private val listener: RecyclerViewClickListener
) : ListAdapter<Image, ImagesListAdapter.ImageViewHolder>(DIFF_CALLBACK) {

    var itemWidth:Int=0
    /**
     * Inflate the view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = calculateItemWidth(parent)


    /**
     * Bind the view with the data
     */
    override fun onBindViewHolder(imageViewHolder: ImageViewHolder, position: Int) =
        imageViewHolder.bind(getItem(position), listener,position)

    /**
     * View Holder Pattern
     */
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * Binds the UI with the data and handles clicks
         */
        fun bind(image: Image, listener: RecyclerViewClickListener,position: Int) =
            with(itemView) {
                val visbility = image?.link?.isEmpty()

                if (!visbility!!) {
                    imageViewLayout.visibility = View.VISIBLE
                    ivItem.load(image?.link) {
                        placeholder(R.drawable.ic_launcher_foreground)
                        error(R.drawable.ic_launcher_background)
                    }
                } else {
                    imageViewLayout.visibility = View.GONE
                }
                setOnClickListener {listener.recyclerViewListClicked(position) }
            }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
                oldItem == newItem
        }
    }
    private fun calculateItemWidth(parent: ViewGroup):ImageViewHolder= run{
        val itemView: View =
            parent.inflate(R.layout.row_image_listitem)
        if (itemWidth === 0) itemWidth = (parent.measuredWidth * .5).toInt()
        ImageViewHolder(itemView)
    }
}