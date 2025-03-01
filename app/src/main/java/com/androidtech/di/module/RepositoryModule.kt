package com.androidtech.di.module

import com.androidtech.data.repository.AppRepositoryImp
import com.androidtech.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAppRepository(appRepository: AppRepositoryImp): AppRepository
}