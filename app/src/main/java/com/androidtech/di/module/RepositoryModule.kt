package com.androidtech.di.module

import com.androidtech.data.repository.AppRepositoryImp
import com.androidtech.data.repository.LocationRepositoryImp
import com.androidtech.data.repository.PhotoRepositoryImp
import com.androidtech.data.repository.UserRepositoryImp
import com.androidtech.data.repository.WeatherRepositoryImp
import com.androidtech.domain.repository.AppRepository
import com.androidtech.domain.repository.LocationRepository
import com.androidtech.domain.repository.PhotoRepository
import com.androidtech.domain.repository.UserRepository
import com.androidtech.domain.repository.WeatherRepository
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
    fun bindWeatherRepository(weatherRepository: WeatherRepositoryImp): WeatherRepository

    @Binds
    fun bindLocationRepository(locationRepository: LocationRepositoryImp): LocationRepository

    @Binds
    fun binPhotoRepository(photoRepositoryImp: PhotoRepositoryImp): PhotoRepository

    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImp: UserRepositoryImp): UserRepository


}