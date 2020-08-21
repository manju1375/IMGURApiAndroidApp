package com.dms.imagesearch.core.mapper

import com.dms.imagesearch.api.response.ImgData
import com.dms.imagesearch.core.storage.entity.ImageDbItem

interface ImagesMapper : Mapper<ImageDbItem, ImgData> {
    override fun ImageDbItem.toRemote(): ImgData {
        return ImgData(
            id = id,
            link = link
        )
    }

    override fun ImgData.toStorage(): ImageDbItem {
        return ImageDbItem(
            id = id,
            comment = null,
            link = link
        )
    }
}