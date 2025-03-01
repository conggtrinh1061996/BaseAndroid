package com.androidtech.domain.extension

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform

/**
 * Map(ánh xạ) 1 giá trị nhận được từ Repository và biến đổi thành Either
 * @param T: chỉ định kiểu dữ liệu (Object, String, Int, Boolean,...)
 * @return
 */
fun <T> Flow<T>.transformToEither(): Flow<Either<Failure, T>> =
    map<T, Either<Failure, T>> {  result: T ->
        Either.OnSuccess(result)
    }.catch {
        Either.OnFailure(it)
    }.flowOn(Dispatchers.IO)

/**
 * Transform resource to either
 * @param T : chỉ định kiểu dữ liệu (Object, String, Int, Boolean,...)
 * @param onSuccess : Nhận kết quả trả về khi thành công
 * @param onError : Nhận lỗi trả về khi có Exception
 */
fun <T> Flow<Resource<T>>.transformResourceToEither(
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
): Flow<Either<Failure, T>> =
    transform { resource ->
        when (resource) {
            is Resource.Success -> emit(Either.OnSuccess(resource.data))
            is Resource.Error -> emit(Either.OnFailure(CustomFailure(Exception(resource.errorMessage))))
        }
    }.onEach { either ->
        either.fold(
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {
                onError(it.error)
            }
        )
    }.catch {
        emit(Either.OnFailure(CustomFailure(it)))
    }.flowOn(Dispatchers.IO)