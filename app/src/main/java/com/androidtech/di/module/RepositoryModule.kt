package com.androidtech.di.module

import android.content.Context
import com.androidtech.app.MainApplication
import com.androidtech.data.repository.AppRepositoryImp
import com.androidtech.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAppRepository(appRepository: AppRepositoryImp): AppRepository

    @Binds
    @Singleton
    fun bindApplicationContext(mainApplication: MainApplication): Context
}