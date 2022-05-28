package com.example.newsapp

import androidx.lifecycle.Observer
import com.example.newsapp.data.api.Response
import com.example.newsapp.domain.usecases.GetHeadlineUseCase
import com.example.newsapp.presentation.MainViewModel
import com.example.newsapp.presentation.model.HeadlinePresentation
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(InstantExecutorExtension::class)
class MainViewModelTest {
    private val headlineUseCase = mockk<GetHeadlineUseCase>()
    private val headlineObserver: Observer<List<HeadlinePresentation>> = mockk {
        every { onChanged(listOf(headLinePresenter)) } just Runs
    }
    private val headLineError = "Internet Error"

    private val errorObserver: Observer<String> = mockk {
        every { onChanged(headLineError) } just Runs
    }

    lateinit var mainViewModel: MainViewModel


    @BeforeEach
    fun setup() {
        mainViewModel = MainViewModel((headlineUseCase)).apply {
            errorLiveData.observeForever(errorObserver)
        }
    }

    @Test
    fun `every call headLineUseCase should active listHeadlinesLiveData`() = runBlockingTest {
        mainViewModel.listHeadlinesLiveData.observeForever(headlineObserver)

        coEvery { headlineUseCase.invoke() } returns
                Response.success(listOf(headLine))

        mainViewModel.getHeadLines()

        verify { headlineObserver.onChanged(listOf(headLinePresenter)) }
    }

    @Test
    fun `ever call headLineUseCase should do a parse with the correct values`() =
        runBlockingTest {
            mainViewModel.listHeadlinesLiveData.observeForever(headlineObserver)
            coEvery { headlineUseCase.invoke() } returns Response.success(
                listOf(headLine)
            )

            mainViewModel.getHeadLines()
            val expected = listOf(headLinePresenter)

            Assert.assertEquals(expected, mainViewModel.listHeadlinesLiveData.value)
        }

    @Test
    fun `every call headLineUseCase should active errorLiveData`() = runBlockingTest {
        coEvery { headlineUseCase.invoke() } returns
                Response.error(Throwable(headLineError))

        mainViewModel.getHeadLines()

        verify { errorObserver.onChanged(headLineError) }
    }
}