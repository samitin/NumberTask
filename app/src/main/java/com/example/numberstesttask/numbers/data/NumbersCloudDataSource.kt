package com.example.numberstesttask.numbers.data

import com.example.numberstesttask.numbers.presentation.FetchNumbers

interface NumbersCloudDataSource : FetchNumber {

    suspend fun randomNumber() : NumberData
}