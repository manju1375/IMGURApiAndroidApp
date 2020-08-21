package com.dms.imagesearch.api.response

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Describes endpoints to fetch the Image search details.
 *
 */
interface ImgSearchService {


    @GET("3/gallery/search/1")
    suspend fun getImgDetails(@Query("q") query: String): Response<ImageResponse>

}
