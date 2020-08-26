package com.dms.imagesearch.core.storage

import androidx.room.*
import com.dms.imagesearch.core.storage.entity.ImageDbItem

import kotlinx.coroutines.flow.Flow

/**
 * Defines access layer to images_list table
 */
@Dao
interface ImagesDao {

    /**
     * Insert images into the table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertImages(articles: List<ImageDbItem>): List<Long>

    @Query("DELETE FROM images_list")
    fun clearAllImages()

    @Transaction
    fun clearAndCacheArticles(images: List<ImageDbItem>) {
        clearAllImages()
        insertImages(images)
    }

    /**
     * Get all the images from table
     */
    @Query("SELECT * FROM images_list")
    fun getImages(): Flow<List<ImageDbItem>>

    /**
     * Get all the images from table
     */
    @Query("SELECT * FROM images_list WHERE id =:imgId")
    fun getImageById(imgId:String): ImageDbItem

    @Update
    fun updateImageItem(vararg imageDbItem: ImageDbItem)
}