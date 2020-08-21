package com.dms.imagesearch.domain

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
    fun getImages(query:String): Flow<ViewState<List<ImageDbItem>>>

    /**
     * Gets fresh images from web.
     */
    suspend fun getImgFromWebservice(query: String): List<ImgData>
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

    override suspend fun getImgFromWebservice(query: String): List<ImgData> {
        return try {
            imgService.getImgDetails(query).body()?.imgData!!
        } catch (e: Exception) {
            println("Error while fetching response: $e.message")
            responseError<ImgData>()
        }
    }
}

@Module
@InstallIn(ApplicationComponent::class)
interface ImagesRepositoryModule {
    /* Exposes the concrete implementation for the interface */
    @Binds fun it(it: DefaultImagesRepository): ImagesRepository
}