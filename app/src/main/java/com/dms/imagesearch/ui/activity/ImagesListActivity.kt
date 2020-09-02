package com.dms.imagesearch.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dms.imagesearch.R
import com.dms.imagesearch.core.utils.RecyclerViewClickListener
import com.dms.imagesearch.core.utils.hideKeyboard
import com.dms.imagesearch.core.utils.observeNotNull
import com.dms.imagesearch.core.utils.saveSelectedImg
import com.dms.imagesearch.ui.ViewState
import com.dms.imagesearch.ui.adapter.ImagesListAdapter
import com.dms.imagesearch.ui.base.BaseActivity
import com.dms.imagesearch.ui.viewmodel.ImagesViewModel
import com.google.android.material.snackbar.Snackbar.make
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty_layout.*
import kotlinx.android.synthetic.main.progress_layout.*


class ImagesListActivity : BaseActivity(), RecyclerViewClickListener {

    private val imageListViewModel: ImagesViewModel by viewModels()
    val imgAdapter = ImagesListAdapter(this)

    /**
     * Starting point of the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting up RecyclerView and adapter
        imagesList.setEmptyView(empty_view)
        imagesList.setProgressView(progress_view)

        imagesList.onlyEmptyView("Enter search query to get results")

        imagesList.adapter = imgAdapter
        imagesList.layoutManager = GridLayoutManager(this, 2)

        initializeRecycler()
    }

    override fun showMessage(isConnected: Boolean) {
        super.showMessage(isConnected)
        if (isNetworkStatustoShow) {
            if (isConnected) {
                make(findViewById(R.id.root_view), "You are Online...", 3000).show()
                isNetworkStatustoShow = false
            } else
                make(findViewById(R.id.root_view), "You are Offline...", 3000).show()
        }
    }


    private fun initializeRecycler() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        imagesList.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
        }
    }

    private fun loadImgData(query: String) {

        imageListViewModel.getImagesFrmCloud(query).observeNotNull(this) { state ->
            when (state) {
                is ViewState.Error -> {
                    imagesList.onlyEmptyView(state.message)
                }
                is ViewState.Success -> {
                    if (state.data.isEmpty()) {
                        imagesList.onlyEmptyView("No Results to display")
                    }
                    imgAdapter.submitList(state.data)
                }
                is ViewState.Loading -> {
                    imagesList.onlyEmptyView("Loading...")
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.queryHint = getString(R.string.search)
        searchView.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 1

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (isNetworkConnected) {
                    try {
                        loadImgData(query!!)
                        hideKeyboard()
                    } catch (e: Exception) {
                        imagesList.onlyEmptyView("${e.message}")
                    }

                } else {
                    make(findViewById(R.id.root_view), "Please check network connection...", 3000).show()
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
        return super.onCreateOptionsMenu(menu!!)
    }


    override fun recyclerViewListClicked(position: Int) {
        saveSelectedImg(imgAdapter.currentList[position].id)
        startActivity(Intent(this, ImageDetailsActivity::class.java))
    }

}
