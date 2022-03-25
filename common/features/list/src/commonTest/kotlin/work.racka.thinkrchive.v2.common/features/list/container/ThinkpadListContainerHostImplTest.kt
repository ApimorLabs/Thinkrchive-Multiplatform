package work.racka.thinkrchive.v2.common.features.list.container

import app.cash.turbine.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.orbitmvi.orbit.test
import util.DataMappers.asDomainModel
import util.Resource
import work.racka.thinkrchive.v2.common.features.list.repository.ListRepository
import work.racka.thinkrchive.v2.common.features.list.util.TestData
import work.racka.thinkrchive.v2.common.features.list.util.TestData.responseListToDbObjectList
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings
import kotlin.test.*

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
    @Ignore
    fun onContainerCreation_IntentShouldProduceStateWithCorrectValues() = runTest {
        val thinkpadResponse = TestData.thinkpadResponseList
        val thinkpadData = thinkpadResponse.responseListToDbObjectList().asDomainModel()
        val query = ""
        //every { settingsRepo.readSortSettings() } returns 0
        coEvery { repo.getAllThinkpadsFromNetwork() } returns flowOf(
            Resource.Success(
                thinkpadResponse
            )
        )
        coEvery { repo.getThinkpadsAlphaAscending(query) } returns flowOf(thinkpadData)
        coEvery { repo.refreshThinkpadList(thinkpadResponse) } returns Unit

        val myState = containerHost.state
        launch {
            myState.test {
                assertTrue(awaitItem().sortOption == 0)
                assertEquals(awaitItem().thinkpadList, thinkpadData)
                awaitComplete()
            }
        }
        val testSubject = containerHost.test()
        //testSubject.runOnCreate()
        testSubject.testIntent {
            //getSortedThinkpadList(query)
            launch {
                /*state.test {
                    println("Turbine running")
                    val item = awaitItem()
                    assertTrue(item.sortOption == 0)
                    val item2 = awaitItem()
                    println(item)
                    assertEquals(item2.thinkpadList, thinkpadData)
                    awaitComplete()
                }*/
            }
        }

        /*testSubject.assert(ThinkpadListState.EmptyState) {
            states(
                { copy(sortOption = 0) },
                { copy(thinkpadList = thinkpadData) }
            )
        }*/
    }
}