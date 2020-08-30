package com.dms.imagesearch.core.mapper

import com.dms.imagesearch.api.response.Image
import com.dms.imagesearch.api.response.ImgData
import com.dms.imagesearch.core.storage.entity.ImageDbItem

interface ImagesMapper : Mapper<ImageDbItem, Image> {
    override fun ImageDbItem.toRemote(): Image {
        return Image(
            id = id,
            link = link
        )
    }

    override fun Image.toStorage(): ImageDbItem {
        return ImageDbItem(
            id = id,
            comment = "",
            link = link
        )
    }
}