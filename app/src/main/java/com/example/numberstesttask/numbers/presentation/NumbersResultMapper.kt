package com.example.numberstesttask.numbers.presentation

import com.example.numberstesttask.numbers.domain.NumberFact
import com.example.numberstesttask.numbers.domain.NumberUiMapper
import com.example.numberstesttask.numbers.domain.NumbersResult

class NumbersResultMapper(
    private val numbersCommunications: NumbersCommunications,
    private val numberUiMapper: NumberFact.Mapper<NumberUi> = NumberUiMapper()
) : NumbersResult.Mapper<Unit> {

    override fun map(list: List<NumberFact>, errorMessage: String) {
        numbersCommunications.showState(
            if (errorMessage.isEmpty()) {
                if (list.isNotEmpty())
                    numbersCommunications.showList(list.map { it.map(numberUiMapper) })
                UiState.Success()
            }else
                UiState.ShowError(errorMessage)
        )
    }
}