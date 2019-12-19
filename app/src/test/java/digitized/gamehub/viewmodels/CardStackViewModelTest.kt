package digitized.gamehub.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import digitized.gamehub.cardStack.CardStackViewModel
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.utilities.CoroutinesTestRule
import io.mockk.clearMocks
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi

class CardStackViewModelTest {
    private lateinit var viewModel: CardStackViewModel

    private val apiService = mockk<GameHubAPI>()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantTestExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        // Hoe krijg ik hier die application
        // viewModel = CardStackViewModel()
    }

    @Test
    fun getResponse() {
        // coEvery { apiService.service.getAllGames() } returns listOf(mockk())
    }

    @After
    fun destroy() {
        clearMocks(apiService)
    }
}
