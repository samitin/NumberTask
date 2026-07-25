package com.example.numberstesttask.numbers.presentation

import com.example.numberstesttask.numbers.domain.BaseTest
import com.example.numberstesttask.numbers.domain.NumberFact
import com.example.numberstesttask.numbers.domain.NumberUiMapper
import com.example.numberstesttask.numbers.domain.NumbersInteractor
import com.example.numberstesttask.numbers.domain.NumbersResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class NumbersViewModelTest : BaseTest(){
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    /**
     * Initial test                            Начальный тест
     * At start fetch data and show it         При запуске извлеките данные и покажите их
     * then try to get some data               затем попытайтесь получить некоторые данные
     * then re-init and check for the result   затем перезапустите и проверьте результат
     */
    private lateinit var communications: TestNumbersCommunications
    private lateinit var interactor: TestNumbersInteractor
    private lateinit var manageResources: TestManageResources
    private lateinit var viewModel: NumbersViewModel
    @Before
    fun init(){
        Dispatchers.setMain(mainThreadSurrogate)
        communications = TestNumbersCommunications()
        interactor = TestNumbersInteractor()
        manageResources = TestManageResources()


        viewModel = NumbersViewModel(
            HandleNumbersRequest.Base(TestDispatchersList(),communications,NumbersResultMapper(communications,NumberUiMapper())),
            manageResources,
            communications,
            interactor,)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
    /**
     * проверить инициализацию и повторную инициализацию
     */
    @Test
    fun `test init and re-init`() = runBlocking{
        //1.init
        interactor.changeExpectedResult(NumbersResult.Success())
        //2.action
        viewModel.init(isFirstRun = true)
        //3.check
        assertEquals(true, communications.progressCalledList[0])
        assertEquals(1, interactor.initCalledList.size)

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)

        assertEquals(true, communications.stateCalledList[0] is UiState.Success)

        assertEquals(0, communications.numbersList.size)
        assertEquals(0, communications.timesShowList)


        //get some data    получить некоторые данные
        interactor.changeExpectedResult(NumbersResult.Failure("No internet connection"))
        viewModel.fetchRandomNumberFact()


        assertEquals(true, communications.progressCalledList[2])

        assertEquals(1, interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.Error("No internet connection"), communications.stateCalledList[1])
        assertEquals(0, communications.timesShowList)

        viewModel.init(isFirstRun = false)
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(0, communications.timesShowList)
    }

    /**
     * Try to get information about empty number   Попробуйте получить информацию о пустом номере
     */
    @Test
    fun `fact about empty number`() = runBlocking{
        manageResources.string = "entered number is empty"
        viewModel.fetchNumberFact("")

        assertEquals(0, interactor.fetchAboutNumberCalledList.size)

        assertEquals(0,communications.progressCalledList.size)

        assertEquals(1,communications.stateCalledList.size)

        assertEquals(UiState.Error("entered number is empty"),communications.stateCalledList[0])

        assertEquals(0,communications.timesShowList)
    }
    /**
     * Try to get information about some number   Попробуйте получить информацию о каком-то номере
     */
    @Test
    fun `fact about some number`() = runBlocking{
        interactor.changeExpectedResult(NumbersResult.Success(listOf(NumberFact("45","number fact about 45"))))
        viewModel.fetchNumberFact("45")

        assertEquals(true,communications.progressCalledList[0])

        assertEquals(1,interactor.fetchAboutNumberCalledList.size)

        assertEquals(NumbersResult.Success(listOf(NumberFact("45","number fact about 45"))),interactor.fetchAboutNumberCalledList[0])

        //assertEquals(NumberFact("45","number fact about 45"),interactor.fetchAboutNumberCalledList[0])

        assertEquals(2,communications.progressCalledList.size)
        assertEquals(false,communications.progressCalledList[1])

        assertEquals(1,communications.stateCalledList.size)
        assertEquals(true, communications.stateCalledList[0] is UiState.Success)

        assertEquals(1,communications.timesShowList)
        assertEquals(NumberUi("45","number fact about 45"),communications.numbersList[0])
    }

    private class TestManageResources : ManageResources {
        var string=""
        override fun string(id: Int): String {
            return string
        }

    }
    private class TestNumbersInteractor : NumbersInteractor {

        private var result : NumbersResult = NumbersResult.Success()
        /**
         * Сохраняет сколько раз вызывалась функция init() и сохраняет result
         */
        val initCalledList = mutableListOf<NumbersResult>()
        /**
         * сохраняет сколько раз вызывалась функция factAboutNumber(number:String) : NumbersResult и сохраняет result
         */
        val fetchAboutNumberCalledList = mutableListOf<NumbersResult>()
        /**
         * Сохраняет сколько раз вызывалась функция factAboutRandomNumber() : NumbersResult исохраняет result
         */
        val fetchAboutRandomNumberCalledList = mutableListOf<NumbersResult>()

        /**
         * Изменить ожидаемый результат меняет result
         */
        fun changeExpectedResult(newResult:NumbersResult){
            result = newResult
        }
        override suspend fun init() : NumbersResult{
            initCalledList.add(result)
            return result
        }
        override suspend fun factAboutNumber(number:String) : NumbersResult{
            fetchAboutNumberCalledList.add(result)
            return result
        }
        override suspend fun factAboutRandomNumber() : NumbersResult{
            fetchAboutRandomNumberCalledList.add(result)
            return result
        }
    }

    private class TestDispatchersList(
        private val testDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    ) : DispatchersList {
        override fun io(): CoroutineDispatcher {
            return testDispatcher
        }

        override fun ui(): CoroutineDispatcher {
            return testDispatcher
        }


    }
}