package com.example.numberstesttask.numbers.presentation

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface DispatchersList {
    fun ui() : CoroutineDispatcher
    fun io() : CoroutineDispatcher

    class Base() : DispatchersList{
        override fun ui(): CoroutineDispatcher = Dispatchers.Main

        override fun io(): CoroutineDispatcher = Dispatchers.IO

    }

}