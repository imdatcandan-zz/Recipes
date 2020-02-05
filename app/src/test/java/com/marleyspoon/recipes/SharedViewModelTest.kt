package com.marleyspoon.recipes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.contentful.java.cda.CDAEntry
import com.marleyspoon.recipes.data.DataRepository
import com.marleyspoon.recipes.model.Recipe
import com.marleyspoon.recipes.viewmodel.SharedViewModel
import com.marleyspoon.recipes.viewmodel.ViewState
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SharedViewModelTest {

    private lateinit var viewModel: SharedViewModel
    private lateinit var mockedObserver: Observer<ViewState>

    @RelaxedMockK
    private lateinit var dataRepository: DataRepository

    companion object {
        val ERROR = Exception("dummy error")
        val recipeList: MutableList<Recipe> = mutableListOf()
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = SharedViewModel(dataRepository)
        mockedObserver = createViewStateObserver()
        viewModel.stateLiveData.observeForever(mockedObserver)
    }

    @Test
    fun testSuccessViewState() {
        runBlockingTest {
            coEvery {
                dataRepository.cdaClient.fetch(CDAEntry::class.java)
            } returns recipeList
        }
        viewModel.fetchRecipeList()
        verifyOrder {
            mockedObserver.onChanged(ViewState.Loading(true))
            mockedObserver.onChanged(ViewState.Success(recipeList))
            mockedObserver.onChanged(ViewState.Loading(false))
        }
    }

    @Test
    fun testErrorViewState() {
        runBlockingTest {
            coEvery {
                dataRepository.cdaClient.fetch(CDAEntry::class.java)
            } throws ERROR
        }
        viewModel.fetchRecipeList()
        verifyOrder {
            mockedObserver.onChanged(ViewState.Loading(true))
            mockedObserver.onChanged(ViewState.Error(ERROR))
            mockedObserver.onChanged(ViewState.Loading(false))
        }
    }

    private fun createViewStateObserver(): Observer<ViewState> = spyk(Observer { })
}