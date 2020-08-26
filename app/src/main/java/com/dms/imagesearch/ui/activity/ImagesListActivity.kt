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
import com.dms.imagesearch.core.utils.*
import com.dms.imagesearch.ui.ViewState
import com.dms.imagesearch.ui.adapter.ImagesAdapter
import com.dms.imagesearch.ui.base.BaseActivity
import com.dms.imagesearch.ui.viewmodel.ImagesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty_layout.*
import kotlinx.android.synthetic.main.progress_layout.*


class ImagesListActivity : BaseActivity(),RecyclerViewClickListener {

    private val imageListViewModel: ImagesViewModel by viewModels()

    val adapter = ImagesAdapter(this)

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
        imagesList.layoutManager = GridLayoutManager(this, 2)

        initializeRecycler()

        // Update the UI on state change

    }


    private fun initializeRecycler() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        imagesList.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            //addOnScrollListener(InfiniteScrollListener({ loadData() }, gridLayoutManager))
        }
    }

    private fun loadData(query: String) {
        imageListViewModel.getImages(query).observeNotNull(this) { state ->
            when (state) {
                is ViewState.Success -> adapter.submitList(state.data)
                is ViewState.Loading -> imagesList.showLoading()
                is ViewState.Error -> toast("Something went wrong ¯\\_(ツ)_/¯ => ${state.message}")
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
                loadData(query!!)
                hideKeyboard()
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
        return super.onCreateOptionsMenu(menu!!)
    }



    override fun recyclerViewListClicked( position: Int) {
        saveSelectedImg(adapter.currentList[position].id)
        startActivity(Intent(this,ImageDetailsActivity::class.java))
    }

}
