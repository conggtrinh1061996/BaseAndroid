package com.androidtech.di.module

import android.content.Context
import com.androidtech.app.MainApplication
import com.androidtech.data.repository.AppRepositoryImp
import com.androidtech.data.repository.AuthRepositoryImp
import com.androidtech.data.repository.NewsRepositoryImp
import com.androidtech.domain.repository.AppRepository
import com.androidtech.domain.repository.AuthRepository
import com.androidtech.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface  RepositoryModule {

    @Binds
    fun bindAppRepository(appRepository: AppRepositoryImp): AppRepository

    @Binds
    fun bindNewsRepository(newsRepository: NewsRepositoryImp): NewsRepository

    @Binds
    fun bindAuthRepository(authRepository: AuthRepositoryImp): AuthRepository


    @Binds
    @Singleton
    fun bindApplicationContext(mainApplication: MainApplication): Context
}