package com.dms.imagesearch.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dms.imagesearch.api.response.Image
import com.dms.imagesearch.core.storage.entity.ImageDbItem
import com.dms.imagesearch.domain.ImagesRepository
import com.dms.imagesearch.ui.ViewState


/**
 * A container for [ImageResponseData] related data to show on the UI.
 */
class ImagesViewModel @ViewModelInject constructor(
    private val imagesRepository: ImagesRepository
) : ViewModel() {

    /**
     * Return images to observeNotNull on the UI.
     */
    fun getImagesFrmCloud(query:String): LiveData<ViewState<List<Image>>> {
        return imagesRepository.getImagesFrmCloud(query).asLiveData()
    }
}