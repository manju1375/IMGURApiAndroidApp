package com.dms.imagesearch.core.storage.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dms.imagesearch.core.storage.entity.ImageDbItem.Images.tableName
import com.dms.imagesearch.core.storage.entity.ImageDbItem.Images.Column


/**
 * Describes how the images are stored.
 */
@Entity(tableName = tableName)
data class ImageDbItem(

    /**
     * Primary key for Room.
     */
    @PrimaryKey(autoGenerate = false)
    var id: String,


    /**
     * comment for particular image
     */
    @ColumnInfo(name = Column.comment)
    val comment: String? = null,

    /**
     * Link of image
     */
    @ColumnInfo(name = Column.link)
    val link: String? = null


) {
    object Images {
        const val tableName = "images_list"

        object Column {
            const val id = "id"
            const val comment = "comment"
            const val link = "link"
        }
    }
}