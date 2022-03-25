package work.racka.thinkrchive.v2.common.features.list.container

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
import states.list.ThinkpadListState
import util.DataMappers.asDomainModel
import util.Resource
import work.racka.thinkrchive.v2.common.features.list.repository.ListRepository
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
    lateinit var repo: ListRepository

    @RelaxedMockK
    lateinit var settingsRepo: MultiplatformSettings

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
    fun onContainerCreation_IntentShouldProduceStateWithCorrectValues() = runTest {
        val thinkpadResponse = TestData.thinkpadResponseList
        val thinkpadData = thinkpadResponse.responseListToDbObjectList().asDomainModel()
        val query = ""
        every { settingsRepo.sortFlow } returns MutableStateFlow(0).asStateFlow()
        coEvery { repo.getAllThinkpadsFromNetwork() } returns flowOf(
            Resource.Success(
                thinkpadResponse
            )
        )
        coEvery { repo.getThinkpadsAlphaAscending(query) } returns flowOf(thinkpadData)
        coEvery { repo.refreshThinkpadList(thinkpadResponse) } returns Unit
        val testSubject = containerHost.test()
        testSubject.runOnCreate()
        testSubject.assert(ThinkpadListState.EmptyState) {
            states(
                { copy(sortOption = 0) },
                { copy(thinkpadList = thinkpadData) }
            )
        }
    }
}