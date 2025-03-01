package com.androidtech.domain.extension

import kotlinx.coroutines.flow.Flow

abstract class UseCase<in Param, Result> {

    abstract fun run(param: Param): Flow<Resource<Result>>

    operator fun invoke(
        param: Param,
        success: (Result) -> Unit,
        error: (Throwable) -> Unit
    ): Flow<Either<Failure, Result>> = run(param).transformResourceToEither(onSuccess = success, onError = error)
}