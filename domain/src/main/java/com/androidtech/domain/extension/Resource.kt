package com.androidtech.domain.extension

/**
 * Resource class
 * Thường chủ yếu sử dụng khi call API hoặc lấy dữ liệu trong Database
 * @param T: Kiểu dữ liệu đầu vào
 * @constructor Create empty Resource
 */
sealed class Resource<T> {
    class Success<T>(val data: T): Resource<T>()
    class Error<T>(val errorMessage: String): Resource<T>()
}