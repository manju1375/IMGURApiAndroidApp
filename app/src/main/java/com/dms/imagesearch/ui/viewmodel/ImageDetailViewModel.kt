package com.dms.imagesearch.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dms.imagesearch.core.storage.entity.ImageDbItem
import com.dms.imagesearch.domain.ImagesRepository

class ImageDetailViewModel @ViewModelInject constructor(private val mImagesRepository: ImagesRepository) :
    ViewModel() {
    val imageDbItem = MutableLiveData<ImageDbItem>()
    fun setImageDbItem(imageItem: ImageDbItem) {
        imageDbItem.value = imageItem
    }

    fun getSelectedImageItem(id: String?): LiveData<ImageDbItem> {
        return mImagesRepository.getImageItem(id!!).asLiveData()
    }

}