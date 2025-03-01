package com.androidtech.domain.extension

sealed class Either<out Failure, out Success> {
    class OnSuccess<out OnSuccess>(val success: OnSuccess): Either<Nothing, OnSuccess>()
    class OnFailure<out OnFailure>(val failure: OnFailure): Either<OnFailure, Nothing>()

    fun fold(onSuccess: (Success) -> Unit, onFailure: (Failure) -> Unit): Any = when (this) {
        is OnSuccess -> onSuccess(success)
        is OnFailure -> onFailure(failure)
    }
}