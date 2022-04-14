package work.racka.thinkrchive.v2.common.features.details.repository

import app.cash.turbine.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
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
import util.DataMappers.asThinkpad
import work.racka.thinkrchive.v2.common.database.dao.ThinkpadDao
import work.racka.thinkrchive.v2.common.features.details.util.TestData
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsRepositoryTest : KoinTest {

    private val repo: DetailsRepository by inject()

    @RelaxedMockK
    lateinit var dao: ThinkpadDao

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockKAnnotations.init(this)
        startKoin {
            modules(
                module {
                    single<DetailsRepository> {
                        DetailsRepositoryImpl(
                            thinkpadDao = dao,
                            backgroundDispatcher = Dispatchers.Default
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
    fun getThinkpad_WhenModelFoundInDb_ReturnsFlowOfTheThinkpad() = runTest {
        val model = "some_thinkpad"
        val thinkpadDb = TestData.ascendingOrderThinkpadsDbObject.first()
        val expect = thinkpadDb.asThinkpad()
        coEvery { dao.getThinkpad(model) } returns flowOf(thinkpadDb)
        val data = repo.getThinkpad(model)
        coVerify { dao.getThinkpad(model) }
        launch {
            data.test {
                val actual = awaitItem()
                assertNotNull(actual)
                assertEquals(expect, actual)
                awaitComplete()
            }
        }
    }

    @Test
    fun getThinkpad_WhenModelNotFoundInDb_ReturnsFlowOfNull() = runTest {
        val model = "definitely_not_a_thinkpad"
        coEvery { dao.getThinkpad(model) } returns flowOf(null)
        val data = repo.getThinkpad(model)
        coVerify { dao.getThinkpad(model) }
        launch {
            data.test {
                val actual = awaitItem()
                assertNull(actual)
                awaitComplete()
            }
        }
    }
}