package com.example.olodjinha.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androiddevs.shoppinglisttestingyt.MainCoroutineRule
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValueTest
import com.example.olodjinha.repositories.MainRepository
import com.example.olodjinha.services.FakeLodjinhaService
import com.example.olodjinha.utils.ResponseWrapper
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: MainViewModel

    val repository: MainRepository = mockk()

    val spyRepository: MainRepository = spyk(MainRepository(FakeLodjinhaService()))

    @Before
    fun setUp() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `validate state loading is setted first and after the data arrived setted to false in getMainHomeData`() {
        mainCoroutineRule.runBlockingTest {

            val response = viewModel.homeLiveData.getOrAwaitValueTest(
                positionOfValueToBeCatch = 1
            ) {
                coEvery { repository.getBanner() } returns ResponseWrapper.Success(null)
                coEvery { repository.getCategories() } returns ResponseWrapper.Success(null)
                coEvery { repository.getMaisVendidos() } returns ResponseWrapper.Success(null)

                viewModel.getMainHomeData()
            }

            assertThat(response[0].loading).isTrue()
            assertThat(response[1].loading).isFalse()
        }
    }

    @Test
    fun `validate that if some of the MainHome Endpoint fail return state error`() {
        mainCoroutineRule.runBlockingTest {

            val response = viewModel.homeLiveData.getOrAwaitValueTest(
                positionOfValueToBeCatch = 1
            ) {
                coEvery { repository.getBanner() } returns ResponseWrapper.Success(null)
                coEvery { repository.getCategories() } returns ResponseWrapper.Success(null)
                coEvery { repository.getMaisVendidos() } returns ResponseWrapper.Error(null)

                viewModel.getMainHomeData()
            }

            assertThat(response[0].loading).isTrue()
            assertThat(response[1].error).isTrue()
        }
    }

    @Test
    fun `validate data is not null when all the 3 requests are successful getMainHomeData`() {
        mainCoroutineRule.runBlockingTest {

            val response = viewModel.homeLiveData.getOrAwaitValueTest(
                positionOfValueToBeCatch = 1
            ) {
                coEvery { repository.getBanner() } returns ResponseWrapper.Success(null)
                coEvery { repository.getCategories() } returns ResponseWrapper.Success(null)
                coEvery { repository.getMaisVendidos() } returns ResponseWrapper.Success(null)

                viewModel.getMainHomeData()
            }

            assertThat(response[0].loading).isTrue()
            assertThat(response[1].data).isNotNull()
        }
    }
}