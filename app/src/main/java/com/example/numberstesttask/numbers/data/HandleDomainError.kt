package com.example.numberstesttask.numbers.data

import com.example.numberstesttask.numbers.domain.HandleError
import com.example.numberstesttask.numbers.domain.NoInternetConnectionException
import com.example.numberstesttask.numbers.domain.ServiceUnavailableException
import java.net.UnknownHostException

class HandleDomainError : HandleError<Exception> {
    override fun handle(e: Exception): Exception = when(e){
        is UnknownHostException -> NoInternetConnectionException()
            else -> ServiceUnavailableException()
    }
}