package com.androidtech.domain.use_case

import com.androidtech.domain.extension.None
import com.androidtech.domain.extension.Resource
import com.androidtech.domain.extension.UseCase
import com.androidtech.domain.model.photo.PhotoObject
import com.androidtech.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoListUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
): UseCase<None, List<PhotoObject>>() {
    override fun run(param: None): Flow<Resource<List<PhotoObject>>> = photoRepository.getPhoto()
}