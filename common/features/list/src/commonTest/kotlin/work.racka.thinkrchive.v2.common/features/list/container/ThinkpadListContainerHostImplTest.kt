package work.racka.thinkrchive.v2.common.features.list.container

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkClass
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
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
import states.settings.ThinkpadSettingsState
import util.DataMappers.asDomainModel
import util.Resource
import work.racka.thinkrchive.v2.common.features.list.repository.ListRepository
import work.racka.thinkrchive.v2.common.features.list.util.TestData
import work.racka.thinkrchive.v2.common.features.list.util.TestData.responseListToDbObjectList
import work.racka.thinkrchive.v2.common.features.settings.AppSettings
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class ThinkpadListContainerHostImplTest : KoinTest {

    private val containerHost: ThinkpadListContainerHostImpl by inject()

    @RelaxedMockK
    lateinit var repo: ListRepository

    @RelaxedMockK
    lateinit var appSettings: AppSettings

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        appSettings = mockkClass(AppSettings::class, relaxed = true)
        repo = mockkClass(ListRepository::class, relaxed = true)
        startKoin {
            modules(
                module {
                    single {
                        ThinkpadListContainerHostImpl(
                            helper = ThinkpadListHelper(repo),
                            settings = appSettings,
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
    fun testing() {
        assertTrue(true)
    }

    @Test
    fun onContainerCreation_IntentShouldProduceStateWithCorrectValues() = runTest {
        val thinkpadResponse = TestData.thinkpadResponseList
        val thinkpadData = thinkpadResponse.responseListToDbObjectList().asDomainModel()
        val query = ""
        every { appSettings.host.state } returns MutableStateFlow(
            ThinkpadSettingsState.State(
                sortValue = 1
            )
        )
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