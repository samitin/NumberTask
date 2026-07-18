package com.example.numberstesttask

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.numberstesttask.numbers.presentation.NumberUi
import com.example.numberstesttask.numbers.presentation.NumbersCommunications
import com.example.numberstesttask.numbers.presentation.UiState

abstract class BaseTest {

    protected class TestNumbersCommunications : NumbersCommunications {

        val progressCalledList = mutableListOf<Boolean>()
        val stateCalledList = mutableListOf<UiState>()
        var timesShowList = 0
        val numbersList = mutableListOf<NumberUi>()

        override fun showProgress(show: Boolean) {
            progressCalledList.add(show)
        }

        override fun showState(state: UiState) {
            stateCalledList.add(state)
        }

        override fun showList(list: List<NumberUi>) {
            timesShowList++
            numbersList.addAll(list)
        }
        override fun observeProgress(owner: LifecycleOwner,observer: Observer<Boolean>) = Unit

        override fun observeState(owner: LifecycleOwner,observer: Observer<UiState>) = Unit

        override fun observeList(owner: LifecycleOwner,observer: Observer<List<NumberUi>>) = Unit
    }
}