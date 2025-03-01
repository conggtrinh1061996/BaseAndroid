package com.androidtech.domain.use_case

import com.androidtech.domain.extension.None
import com.androidtech.domain.extension.Resource
import com.androidtech.domain.extension.UseCase
import com.androidtech.domain.model.Demo
import com.androidtech.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDemoListUseCase @Inject constructor(
    private val appRepository: AppRepository
): UseCase<None, Demo>() {

    override fun run(param: None): Flow<Resource<Demo>> = appRepository.getDemo()
}