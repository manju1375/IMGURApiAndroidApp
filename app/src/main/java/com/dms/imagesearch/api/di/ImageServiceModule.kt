package com.dms.imagesearch.api.di

import com.dms.imagesearch.api.response.ImgSearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ImageServiceModule {

    private const val BASE_URL = "https://api.imgur.com/"

    @Singleton
    @Provides
    fun provideImgSearchService(): ImgSearchService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ImgSearchService::class.java)
    }
}