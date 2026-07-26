package com.example.numberstesttask.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberstesttask.R
import com.example.numberstesttask.numbers.domain.NumbersInteractor


class NumbersViewModel(
    private val handleResult: HandleNumbersRequest,
    private val manageResources: ManageResources,
    private val communications : NumbersCommunications,
    private val interactor : NumbersInteractor,
) : ObserveNumbers , FetchNumbers,ViewModel(), ClearError{

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
        communications.observeProgress(owner, observer)
    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
        communications.observeState(owner, observer)
    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
        communications.observeList(owner, observer)

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun){
            handleResult.handle(viewModelScope){
                interactor.init()
            }
        }
    }

    override fun fetchRandomNumberFact() =
        handleResult.handle(viewModelScope){
        interactor.factAboutRandomNumber()
    }

    override fun fetchNumberFact(number: String) {
        if (number.isEmpty())
            communications.showState(UiState.ShowError(manageResources.string(R.string.empty_number_error_message)))
        else
            handleResult.handle(viewModelScope) {
                interactor.factAboutNumber(number)
            }
    }
    override fun clearError() = communications.showState(UiState.ClearError())
}
interface FetchNumbers{
    /**
     * Инициализация
     */
    fun init(isFirstRun : Boolean)

    /**
     * Получить факт о случайном числе
     */
    fun fetchRandomNumberFact()
    /**
     * Получить факт о числе
     */
    fun fetchNumberFact(number : String)
}
interface ClearError {
    fun clearError()
}