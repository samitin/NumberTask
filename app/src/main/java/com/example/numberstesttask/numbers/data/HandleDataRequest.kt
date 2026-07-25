package com.example.numberstesttask.numbers.data

import com.example.numberstesttask.numbers.domain.HandleError
import com.example.numberstesttask.numbers.domain.NumberFact

interface HandleDataRequest {

    suspend fun handle(block:suspend () -> NumberData): NumberFact

    class Base(
        private val cacheDataSource: NumbersCacheDataSource,
        private val mapperToDomain: NumberDataToDomain,
        private val handleError: HandleError<Exception>
    ) : HandleDataRequest{
        override suspend fun handle(block: suspend () -> NumberData): NumberFact = try{
            val result = block.invoke()
            cacheDataSource.saveNumber(result)
            result.map(mapperToDomain)
        }catch (e: Exception){
            throw handleError.handle(e)
        }

    }
}