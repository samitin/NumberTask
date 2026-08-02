package com.example.numberstesttask.main.sl

import android.content.Context
import com.example.numberstesttask.numbers.data.cache.CacheModule
import com.example.numberstesttask.numbers.data.cache.NumbersDataBase
import com.example.numberstesttask.numbers.data.cloud.CloudModule
import com.example.numberstesttask.numbers.presentation.DispatchersList
import com.example.numberstesttask.numbers.presentation.ManageResources

interface Core : CloudModule, CacheModule, ManageResources {

    fun provideDispatchersList() : DispatchersList

    class Base(
        private val context: Context,
        private val isRelease: Boolean
    ) : Core{

        private val manageResources : ManageResources = ManageResources.Base(context)
        private val dispatchersList by lazy { DispatchersList.Base() }
        private val cloudModule by lazy {
            if (isRelease)
                CloudModule.Release()
            else
                CloudModule.Debug()
        }
        private val cacheModule by lazy {
            if (isRelease)
                CacheModule.Base(context)
            else
                CacheModule.Mock(context)
        }
        override fun provideDispatchersList(): DispatchersList = dispatchersList

        override fun <T> service(clasz: Class<T>): T = cloudModule.service(clasz)

        override fun provideDataBase(): NumbersDataBase = cacheModule.provideDataBase()

        override fun string(id: Int): String = manageResources.string(id)

    }
}