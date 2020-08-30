package com.dms.imagesearch.domain

import androidx.lifecycle.LiveData
import com.dms.imagesearch.api.response.Image
import com.dms.imagesearch.api.response.ImgData
import com.dms.imagesearch.api.response.ImgSearchService
import com.dms.imagesearch.core.mapper.ImagesMapper
import com.dms.imagesearch.core.storage.ImagesDao
import com.dms.imagesearch.core.storage.entity.ImageDbItem
import com.dms.imagesearch.core.utils.responseError
import com.dms.imagesearch.ui.ViewState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.w3c.dom.Comment
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository abstracts the logic of fetching the data and persisting it for
 * offline. They are the data source as the single source of truth.
 */
interface ImagesRepository {

    /**
     * Gets the cached  image from database and tries to get
     * fresh  images from web and save into database
     * if that fails then continues showing cached data.
     */
    fun getImages(query: String): Flow<ViewState<List<ImageDbItem>>>

    /**
     * Gets single image based on ID.
     */
     fun getImageItem(imageId: String): Flow<ImageDbItem>

    /**
     * update image with comment in Local Db.
     */
    suspend fun updateImageWithComments(comment: String,id: String)

    /**
     * Gets fresh images from web.
     */
    suspend fun getImgFromWebservice(query: String): List<Image>
    suspend fun insertImageItem(imgItm: ImageDbItem)

    fun getImagesFrmCloud(query: String): Flow<ViewState<List<Image>>>

}

@Singleton
class DefaultImagesRepository @Inject constructor(
    private val imagesDao: ImagesDao,
    private val imgService: ImgSearchService
) : ImagesRepository, ImagesMapper {

    override fun getImages(query: String): Flow<ViewState<List<ImageDbItem>>> = flow {
        // 1. Start with loading
        emit(ViewState.loading())

        // 2. Try to fetch fresh images from web + cache if any
        val freshNews = getImgFromWebservice(query)
        freshNews?.toStorage()?.let(imagesDao::insertImages)

        // 3. Get Images from cache [cache is always source of truth]
        val cachedNews = imagesDao.getImages()
        emitAll(cachedNews.map { ViewState.success(it) })
    }
        .flowOn(Dispatchers.IO)

    override fun getImageItem(imageId: String): Flow<ImageDbItem> = flow {
        emit(imagesDao.getImageById(imageId))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateImageWithComments(comment: String,id:String) =
        imagesDao.updateImageItem(comment,id);





    override suspend fun getImgFromWebservice(query: String): List<Image> {
        var imagesRes = mutableListOf<Image>()
        try {
            val imgData = imgService.getImgDetails(query).body()?.imgData
            for (idx in imgData!!.indices) {
                for (jdx in imgData?.get(idx)?.images!!.indices) {
                    imagesRes.add(imgData[idx].images!![jdx])
                }
            }
        } catch (e: Exception) {
            println("Error while fetching response: $e.message")
            responseError<Image>()
        }
        return imagesRes
    }


    override fun getImagesFrmCloud(query: String): Flow<ViewState<List<Image>>> = flow<ViewState<List<Image>>>{
        var result:LiveData<List<Image>>
        var imagesRes = mutableListOf<Image>()
        try {
            val imgData = imgService.getImgDetails(query).body()?.imgData
            for (idx in imgData!!.indices) {
                for (jdx in imgData?.get(idx)?.images!!.indices) {
                    imagesRes.add(imgData[idx].images!![jdx])
                }
            }
        } catch (e: Exception) {
            println("Error while fetching response: $e.message")
            responseError<Image>()
        }
        imagesRes?.toStorage()?.let(imagesDao::insertImages)
        val res:Flow<List<Image>> = flowOf(imagesRes)
        emitAll(res.map { ViewState.success(it) })
    }.flowOn(Dispatchers.IO)





    override suspend fun insertImageItem(imgItm: ImageDbItem)=
        imagesDao.insertImage(imgItm)



    @Module
    @InstallIn(ApplicationComponent::class)
    interface ImagesRepositoryModule {
        /* Exposes the concrete implementation for the interface */
        @Binds
        fun it(it: DefaultImagesRepository): ImagesRepository
    }
}