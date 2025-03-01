package com.androidtech.domain.extension

sealed class Failure

class CustomFailure(val error: Throwable): Failure()

class None