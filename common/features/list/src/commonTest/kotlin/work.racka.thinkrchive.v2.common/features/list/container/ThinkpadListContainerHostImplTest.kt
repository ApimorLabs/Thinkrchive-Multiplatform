package work.racka.thinkrchive.v2.common.features.list.container

import domain.Thinkpad
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.orbitmvi.orbit.test
import states.list.ThinkpadListSideEffect
import states.list.ThinkpadListState
import util.DataMappers.asDomainModel
import util.NetworkError
import util.Resource
import work.racka.thinkrchive.v2.common.features.list.repository.ListRepository
import work.racka.thinkrchive.v2.common.features.list.util.Constants
import work.racka.thinkrchive.v2.common.features.list.util.TestData
import work.racka.thinkrchive.v2.common.features.list.util.TestData.responseListToDbObjectList
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ThinkpadListContainerHostImplTest : KoinTest {

    private val containerHost: ThinkpadListContainerHostImpl by inject()

    @RelaxedMockK
    private lateinit var repo: ListRepository

    @RelaxedMockK
    private lateinit var settingsRepo: MultiplatformSettings

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockKAnnotations.init(this)
        startKoin {
            modules(
                module {
                    single {
                        ThinkpadListContainerHostImpl(
                            helper = ThinkpadListHelper(repo),
                            settingsRepo = settingsRepo,
                            scope = CoroutineScope(Dispatchers.Default)
                        )
                    }
                }
            )
        }
    }

    @AfterTest
    fun teardown() {
        stopKoin()
        unmockkAll()
    }

    @Test
    fun onContainerCreation_WhenNetworkResponseIsSuccess_ProducesStateWithNetworkLoadingFalseSideEffect() =
        runTest {
            val thinkpadResponse = TestData.thinkpadResponseList
            val thinkpadData = thinkpadResponse.responseListToDbObjectList().asDomainModel()
            val query = ""
            val sortOption = 0

            // Mocking
            every { settingsRepo.sortFlow } returns flowOf(sortOption)
            coEvery { repo.getAllThinkpadsFromNetwork() } returns flowOf(
                Resource.Success(
                    thinkpadResponse
                )
            )
            coEvery { repo.getThinkpadsAlphaAscending(query) } returns flowOf(thinkpadData)
            coEvery { repo.refreshThinkpadList(thinkpadResponse) } returns Unit

            // Testing
            val testSubject = containerHost.test()
            testSubject.runOnCreate()

            verify { settingsRepo.sortFlow }
            coVerify { repo.getAllThinkpadsFromNetwork() }
            coVerify { repo.getThinkpadsAlphaAscending(query) }
            coVerify { repo.refreshThinkpadList(thinkpadResponse) }
            testSubject.assert(ThinkpadListState.EmptyState) {
                states(
                    { copy(sortOption = sortOption) },
                    { copy(thinkpadList = thinkpadData) }
                )
                postedSideEffects(
                    ThinkpadListSideEffect.NoSideEffect
                )
            }
        }

    // We also have to verify repo.refreshThinkpadList() is never called
    @Test
    fun onContainerCreation_WhenNetworkResponseIsLoading_ProducesStateWithNetworkLoadingTrueSideEffect() =
        runTest {
            val thinkpadResponse = TestData.thinkpadResponseList
            val thinkpadData = thinkpadResponse.responseListToDbObjectList().asDomainModel()
            val query = ""
            val sortOption = 1

            // Mocking
            every { settingsRepo.sortFlow } returns flowOf(sortOption)
            coEvery { repo.getAllThinkpadsFromNetwork() } returns flowOf(
                Resource.Loading()
            )
            coEvery { repo.getThinkpadsNewestFirst(query) } returns flowOf(thinkpadData)
            coEvery { repo.refreshThinkpadList(thinkpadResponse) } returns Unit

            // Testing
            val testSubject = containerHost.test()
            testSubject.runOnCreate()

            verify { settingsRepo.sortFlow }
            coVerify { repo.getAllThinkpadsFromNetwork() }
            coVerify { repo.getThinkpadsNewestFirst(query) }
            coVerify(exactly = 0) { repo.refreshThinkpadList(thinkpadResponse) }
            testSubject.assert(ThinkpadListState.EmptyState) {
                states(
                    { copy(networkLoading = true) },
                    { copy(sortOption = sortOption) },
                    { copy(thinkpadList = thinkpadData) }
                )
                postedSideEffects(
                    ThinkpadListSideEffect.NoSideEffect
                )
            }
        }

    // We should receive NetworkError.NoInternetError
    // We also have to verify repo.refreshThinkpadList() is never called
    @Test
    fun onContainerCreation_WhenNetworkResponseIsNoInternetError_ProducesStateWithNetworkLoadingFalseAndShowErrorSideEffect() =
        runTest {
            val thinkpadResponse = TestData.thinkpadResponseList
            val thinkpadData = thinkpadResponse.responseListToDbObjectList().asDomainModel()
            val query = ""
            val errorMsg = "This is an error message"
            val sortOption = 0

            // Mocking
            every { settingsRepo.sortFlow } returns flowOf(sortOption)
            coEvery { repo.getAllThinkpadsFromNetwork() } returns flowOf(
                Resource.Error(message = errorMsg, errorCode = NetworkError.NoInternetError)
            )
            coEvery { repo.getThinkpadsAlphaAscending(query) } returns flowOf(thinkpadData)
            coEvery { repo.refreshThinkpadList(thinkpadResponse) } returns Unit

            // Testing
            val testSubject = containerHost.test()
            testSubject.runOnCreate()

            verify { settingsRepo.sortFlow }
            coVerify { repo.getAllThinkpadsFromNetwork() }
            coVerify { repo.getThinkpadsAlphaAscending(query) }
            coVerify(exactly = 0) { repo.refreshThinkpadList(thinkpadResponse) }
            testSubject.assert(ThinkpadListState.EmptyState) {
                states(
                    { copy(sortOption = sortOption) },
                    { copy(thinkpadList = thinkpadData) }
                )
                postedSideEffects(
                    ThinkpadListSideEffect.ShowNetworkErrorSnackbar(
                        msg = Constants.NO_INTERNET_ERROR,
                        networkError = NetworkError.NoInternetError
                    ),
                    ThinkpadListSideEffect.NoSideEffect,
                )
            }
        }

    // We should receive NetworkError.SerializationError
    // We also have to verify repo.refreshThinkpadList() is never called
    @Test
    fun onContainerCreation_WhenNetworkResponseIsSerializationError_ProducesStateWithNetworkLoadingFalseAndShowErrorSideEffect() =
        runTest {
            val thinkpadResponse = TestData.thinkpadResponseList
            val thinkpadData = thinkpadResponse.responseListToDbObjectList().asDomainModel()
            val query = ""
            val errorMsg = "This is an error message"
            val sortOption = 0

            // Mocking
            every { settingsRepo.sortFlow } returns flowOf(sortOption)
            coEvery { repo.getAllThinkpadsFromNetwork() } returns flowOf(
                Resource.Error(message = errorMsg, errorCode = NetworkError.SerializationError)
            )
            coEvery { repo.getThinkpadsAlphaAscending(query) } returns flowOf(thinkpadData)
            coEvery { repo.refreshThinkpadList(thinkpadResponse) } returns Unit

            // Testing
            val testSubject = containerHost.test()
            testSubject.runOnCreate()

            verify { settingsRepo.sortFlow }
            coVerify { repo.getAllThinkpadsFromNetwork() }
            coVerify { repo.getThinkpadsAlphaAscending(query) }
            coVerify(exactly = 0) { repo.refreshThinkpadList(thinkpadResponse) }
            testSubject.assert(ThinkpadListState.EmptyState) {
                states(
                    { copy(sortOption = sortOption) },
                    { copy(thinkpadList = thinkpadData) }
                )
                postedSideEffects(
                    ThinkpadListSideEffect.ShowNetworkErrorSnackbar(
                        msg = Constants.SERIALIZATION_ERROR,
                        networkError = NetworkError.SerializationError
                    ),
                    ThinkpadListSideEffect.NoSideEffect
                )
            }
        }

    // We should receive NetworkError.StatusCodeError
    // We also have to verify repo.refreshThinkpadList() is never called
    @Test
    fun onContainerCreation_WhenNetworkResponseIsStatusCodeError_ProducesStateWithNetworkLoadingFalseAndShowErrorSideEffect() =
        runTest {
            val thinkpadResponse = TestData.thinkpadResponseList
            val thinkpadData = thinkpadResponse.responseListToDbObjectList().asDomainModel()
            val query = ""
            val errorMsg = "This is an error message"
            val sortOption = 0

            // Mocking
            every { settingsRepo.sortFlow } returns flowOf(sortOption)
            coEvery { repo.getAllThinkpadsFromNetwork() } returns flowOf(
                Resource.Error(message = errorMsg, errorCode = NetworkError.StatusCodeError)
            )
            coEvery { repo.getThinkpadsAlphaAscending(query) } returns flowOf(thinkpadData)
            coEvery { repo.refreshThinkpadList(thinkpadResponse) } returns Unit

            // Testing
            val testSubject = containerHost.test()
            testSubject.runOnCreate()

            verify { settingsRepo.sortFlow }
            coVerify { repo.getAllThinkpadsFromNetwork() }
            coVerify { repo.getThinkpadsAlphaAscending(query) }
            coVerify(exactly = 0) { repo.refreshThinkpadList(thinkpadResponse) }
            testSubject.assert(ThinkpadListState.EmptyState) {
                states(
                    { copy(sortOption = sortOption) },
                    { copy(thinkpadList = thinkpadData) }
                )
                postedSideEffects(
                    ThinkpadListSideEffect.ShowNetworkErrorSnackbar(
                        msg = Constants.RESPONSE_CODE_ERROR,
                        networkError = NetworkError.StatusCodeError
                    ),
                    ThinkpadListSideEffect.NoSideEffect
                )
            }
        }

    @Test
    fun getSortedThinkpadList_WhenCalled_RetrievesSortedThinkpadsFromDb() = runTest {
        val thinkpadResponse = TestData.thinkpadResponseList
        val thinkpadData = thinkpadResponse.responseListToDbObjectList().asDomainModel()
        val query = ""
        val sortOption = 2

        // Mocking
        coEvery { repo.getThinkpadsOldestFirst(query) } returns flowOf(thinkpadData)

        // Testing
        val testSubject = containerHost
            .test(
                ThinkpadListState.State(
                    thinkpadList = listOf(),
                    sortOption = sortOption
                )
            )
        testSubject.testIntent {
            getSortedThinkpadList(query)
        }

        coVerify(exactly = 0) { repo.getThinkpadsAlphaAscending(query) }
        coVerify { repo.getThinkpadsOldestFirst(query) }
        testSubject.assert(ThinkpadListState.State(listOf(), sortOption)) {
            states(
                { copy(sortOption = sortOption, thinkpadList = listOf()) },
                { copy(thinkpadList = thinkpadData) }
            )
        }
    }

    @Test
    fun getSortedThinkpadList_WhenSearchedItemFound_RetrievesSortedThinkpadsFromDbWithItem() =
        runTest {
            val thinkpadData = TestData.ascendingOrderThinkpad // Last Item is X250
            val oneThinkpad = listOf(thinkpadData.last()) // Will only have the X250
            val emptyQuery = ""
            val query = "X250"
            val sortOption = 0

            // Mocking
            // emptyQuery should return thinkpadData as is then query will return specific data (oneThinkpad)
            coEvery { repo.getThinkpadsAlphaAscending(emptyQuery) } returns flowOf(thinkpadData)
            coEvery { repo.getThinkpadsAlphaAscending(query) } returns flowOf(oneThinkpad)

            // Testing
            val testSubject = containerHost
                .test(
                    ThinkpadListState.State(
                        thinkpadList = listOf(),
                        sortOption = sortOption
                    )
                )
            testSubject.testIntent { getSortedThinkpadList(emptyQuery) }
            // New Search
            testSubject.testIntent { getSortedThinkpadList(query) }

            coVerify(exactly = 1) { repo.getThinkpadsAlphaAscending(emptyQuery) }
            coVerify(exactly = 1) { repo.getThinkpadsAlphaAscending(query) }
            testSubject.assert(ThinkpadListState.State(listOf(), sortOption)) {
                states(
                    { copy(thinkpadList = thinkpadData) },
                    { copy(thinkpadList = oneThinkpad) }
                )
            }
        }

    @Test
    fun getSortedThinkpadList_WhenSearchedItemNotFound_RetrievesEmptyThinkpadListFromDb() =
        runTest {
            val thinkpadData = TestData.ascendingOrderThinkpad // Last Item is X250
            val emptyThinkpadList = emptyList<Thinkpad>() // Will only have the X250
            val emptyQuery = ""
            val query = "Definitely not a Thinkpad"
            val sortOption = 0

            // Mocking
            // emptyQuery should return thinkpadData as is then query will return an empty list
            coEvery { repo.getThinkpadsAlphaAscending(emptyQuery) } returns flowOf(thinkpadData)
            coEvery { repo.getThinkpadsAlphaAscending(query) } returns flowOf(emptyThinkpadList)

            // Testing
            val testSubject = containerHost
                .test(
                    ThinkpadListState.State(
                        thinkpadList = listOf(),
                        sortOption = sortOption
                    )
                )
            testSubject.testIntent { getSortedThinkpadList(emptyQuery) }
            // New Search
            testSubject.testIntent { getSortedThinkpadList(query) }

            coVerify(exactly = 1) { repo.getThinkpadsAlphaAscending(emptyQuery) }
            coVerify(exactly = 1) { repo.getThinkpadsAlphaAscending(query) }
            testSubject.assert(ThinkpadListState.State(listOf(), sortOption)) {
                states(
                    { copy(thinkpadList = thinkpadData) },
                    { copy(thinkpadList = emptyThinkpadList) }
                )
            }
        }

    @Test
    fun sortSelected_WhenNewSortOptionSelected_RetrievesNewSortedThinkpadListFromDb() =
        runTest {
            val defaultSortOption = 0
            val newSortOption = 2

            // Testing
            val testSubject = spyk(containerHost)
                .test(
                    ThinkpadListState.State(
                        thinkpadList = listOf(),
                        sortOption = defaultSortOption
                    )
                )
            testSubject.testIntent { sortSelected(defaultSortOption) }
            // New Search
            testSubject.testIntent { sortSelected(newSortOption) }

            testSubject.assert(ThinkpadListState.State(listOf(), defaultSortOption)) {
                states(
                    { copy(sortOption = defaultSortOption) },
                    { copy(sortOption = newSortOption) }
                )
            }
        }
}