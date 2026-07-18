package com.example.numberstesttask.numbers.domain

import com.example.numberstesttask.numbers.presentation.NumberUi

class NumberUiMapper : NumberFact.Mapper<NumberUi> {
    override fun map(id: String, fact: String): NumberUi =
        NumberUi(id, fact)
}