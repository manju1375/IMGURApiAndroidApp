package com.dms.imagesearch.api.response

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Describes endpoints to fetch the Image search details.
 *
 */
interface ImgSearchService {

    @Headers("Authorization: Client-ID 137cda6b5008a7c")
    @GET("3/gallery/search/1")
    suspend fun getImgDetails(@Query("q") query: String): Response<ImageResponse>

}
