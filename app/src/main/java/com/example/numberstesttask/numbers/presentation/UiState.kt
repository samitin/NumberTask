package com.example.numberstesttask.numbers.presentation

import android.os.Message
import com.example.numberstesttask.numbers.domain.NumberFact

sealed class UiState {
    interface Mapper<T> {
        fun map(message: String): T
    }

    abstract fun <T> map(mapper: Mapper<T>): T

    class Success(): UiState() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map("")
    }

    data class Error(private val errorMassage: String): UiState() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(errorMassage)
    }
}