package com.example.numberstesttask.numbers.sl

import com.example.numberstesttask.main.sl.Core
import com.example.numberstesttask.main.sl.Module
import com.example.numberstesttask.numbers.data.BaseNumbersRepository
import com.example.numberstesttask.numbers.data.HandleDataRequest
import com.example.numberstesttask.numbers.data.HandleDomainError
import com.example.numberstesttask.numbers.data.NumberDataToDomain
import com.example.numberstesttask.numbers.data.cache.NumberDataToCache
import com.example.numberstesttask.numbers.data.cache.NumbersCacheDataSource
import com.example.numberstesttask.numbers.data.cloud.NumbersCloudDataSource
import com.example.numberstesttask.numbers.data.cloud.NumbersService
import com.example.numberstesttask.numbers.domain.HandleError
import com.example.numberstesttask.numbers.domain.HandleRequest
import com.example.numberstesttask.numbers.domain.NumberUiMapper
import com.example.numberstesttask.numbers.domain.NumbersInteractor
import com.example.numberstesttask.numbers.presentation.HandleNumbersRequest
import com.example.numberstesttask.numbers.presentation.NumbersCommunications
import com.example.numberstesttask.numbers.presentation.NumbersListCommunication
import com.example.numberstesttask.numbers.presentation.NumbersResultMapper
import com.example.numberstesttask.numbers.presentation.NumbersStateCommunication
import com.example.numberstesttask.numbers.presentation.NumbersViewModel
import com.example.numberstesttask.numbers.presentation.ProgressCommunication

class NumbersModule(private val core: Core) : Module<NumbersViewModel> {

    override fun viewModel(): NumbersViewModel {
        val communications = NumbersCommunications.Base(
            ProgressCommunication.Base(),
            NumbersStateCommunication.Base(),
            NumbersListCommunication.Base()
        )
        val cacheDataSource = NumbersCacheDataSource.Base(
            core.provideDataBase().numbersDao(),
            NumberDataToCache()
        )
        val repository = BaseNumbersRepository(
            NumbersCloudDataSource.Base(
                core.service(NumbersService::class.java)
            ),
            cacheDataSource,
            HandleDataRequest.Base(
                cacheDataSource,
                NumberDataToDomain(),
                HandleDomainError()
            ),
            NumberDataToDomain()
        )
        return NumbersViewModel(
            HandleNumbersRequest.Base(
                core.provideDispatchersList(),
                communications,
                NumbersResultMapper(communications, NumberUiMapper())
            ),
            core,
            communications,
            NumbersInteractor.Base(
                repository,
                HandleRequest.Base(
                    HandleError.Base(core),
                    repository
                )
            )
        )
    }
}