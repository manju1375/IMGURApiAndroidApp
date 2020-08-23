package com.dms.imagesearch.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dms.imagesearch.R
import com.dms.imagesearch.core.utils.observeNotNull
import com.dms.imagesearch.core.utils.toast
import com.dms.imagesearch.ui.ViewState
import com.dms.imagesearch.ui.adapter.ImagesAdapter
import com.dms.imagesearch.ui.adapter.InfiniteScrollListener
import com.dms.imagesearch.ui.base.BaseActivity
import com.dms.imagesearch.ui.viewmodel.ImagesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty_layout.*
import kotlinx.android.synthetic.main.progress_layout.*


class ImagesListActivity : BaseActivity() {

    private val imageListViewModel: ImagesViewModel by viewModels()
    private var pageNo = 0;
    val adapter = ImagesAdapter { toast("Clicked on item") }

    /**
     * Starting point of the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting up RecyclerView and adapter
        imagesList.setEmptyView(empty_view)
        imagesList.setProgressView(progress_view)


        imagesList.adapter = adapter
        imagesList.layoutManager = GridLayoutManager(this,2)

        initializeRecycler()

        // Update the UI on state change
     loadData()
    }


    private fun initializeRecycler() {
        val gridLayoutManager = GridLayoutManager(this,2)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        imagesList.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            addOnScrollListener(InfiniteScrollListener({ loadData() }, gridLayoutManager))
        }
    }

    private fun loadData() {
        imageListViewModel.getImages("hollywood").observeNotNull(this) { state ->
            when (state) {
                is ViewState.Success -> adapter.submitList(state.data)
                is ViewState.Loading -> imagesList.showLoading()
                is ViewState.Error -> toast("Something went wrong ¯\\_(ツ)_/¯ => ${state.message}")
            }
        }

    }

}
