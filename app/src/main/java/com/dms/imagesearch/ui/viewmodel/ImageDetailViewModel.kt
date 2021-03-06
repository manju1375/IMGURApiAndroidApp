package com.dms.imagesearch.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dms.imagesearch.core.storage.entity.ImageDbItem
import com.dms.imagesearch.domain.ImagesRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImageDetailViewModel @ViewModelInject constructor(private val mImagesRepository: ImagesRepository) :
    ViewModel() {
    val imageDbItem = MutableLiveData<ImageDbItem>()
    fun setImageDbItem(imageItem: ImageDbItem) {
        imageDbItem.value = imageItem
    }

    fun getSelectedImageItem(id: String?): LiveData<ImageDbItem> {
        return mImagesRepository.getImageItem(id!!).asLiveData()
    }

    fun updateImageWithComments(comment: String, id: String) {
        GlobalScope.launch {
            mImagesRepository.updateImageWithComments(comment, id)
        }
    }

    fun insertImageItem(imgItm: ImageDbItem) {
        GlobalScope.launch {
            mImagesRepository.insertImageItem(imgItm)
        }
    }


}