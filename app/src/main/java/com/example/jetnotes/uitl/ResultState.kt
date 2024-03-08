package com.example.jetnotes.uitl

sealed class ResultState<out T> {
    object Idle: ResultState<Nothing>()
    object Loading: ResultState<Nothing>()
    data class Success<out T>(val data: T): ResultState<T>()
    data class Error(val exception: Throwable): ResultState<Nothing>()
}