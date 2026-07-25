package com.example.numberstesttask.numbers.domain

import com.example.numberstesttask.R
import com.example.numberstesttask.numbers.presentation.ManageResources

interface HandleError <T>{
    fun handle(e : Exception) : T

    class Base(private val manageResources: ManageResources) : HandleError <String>{
        override fun handle(e: Exception): String =
            when(e){
                is NoInternetConnectionException -> manageResources.string(R.string.no_connection_message)
                else -> manageResources.string(R.string.service_is_unavailable)
            }

    }
}

abstract class DomainException : Exception()
class NoInternetConnectionException : DomainException()
class ServiceUnavailableException : DomainException()