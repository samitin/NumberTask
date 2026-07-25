package com.example.numberstesttask.numbers.domain

import com.example.numberstesttask.numbers.presentation.NumbersResultMapper
import com.example.numberstesttask.numbers.presentation.NumberUi
import com.example.numberstesttask.numbers.presentation.UiState
import junit.framework.TestCase.assertEquals
import org.junit.Test

class NumberResultMapperTest : BaseTest() {

    @Test
    fun test_error(){
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

        mapper.map(emptyList(),"not empty massage")

        assertEquals(UiState.Error("not empty massage"),communications.stateCalledList[0])
        //assertEquals(UiState.Success(), UiState.Success())
    }

    @Test
    fun test_success_no_list(){
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

        mapper.map(emptyList(),"")

        assertEquals(0,communications.timesShowList)
        assertEquals(true,communications.stateCalledList[0] is UiState.Success)
    }

    @Test
    fun test_success_with_list() {
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

        mapper.map(listOf(NumberFact("5","fact 5")), "")
        assertEquals(true,communications.stateCalledList[0] is UiState.Success)
        assertEquals(1,communications.timesShowList)
        assertEquals(NumberUi("5","fact 5"),communications.numbersList[0])
    }
}