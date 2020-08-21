package com.dms.imagesearch.api.di

import android.app.Application
import com.dms.imagesearch.core.storage.ImagesDao
import com.dms.imagesearch.core.storage.entity.ImagesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
    object ImagesDatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): ImagesDatabase = ImagesDatabase.buildDefault(app)

    @Singleton
    @Provides
    fun provideUserDao(db: ImagesDatabase): ImagesDao = db.imagesDao()
}