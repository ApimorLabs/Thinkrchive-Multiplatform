package work.racka.thinkrchive.v2.common.features.list.repository

import app.cash.turbine.test
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.mockk.verify
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
import util.DataMappers.asDomainModel
import work.racka.thinkrchive.v2.common.database.dao.ThinkpadDao
import work.racka.thinkrchive.v2.common.features.list.util.TestData
import work.racka.thinkrchive.v2.common.network.remote.ThinkrchiveApi
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ListRepositoryTest : KoinTest {

    private val repo: ListRepository by inject()

    @RelaxedMockK
    lateinit var api: ThinkrchiveApi

    @RelaxedMockK
    lateinit var dao: ThinkpadDao

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin {
            modules(
                module {
                    single<ListRepository> {
                        ListRepositoryImpl(
                            thinkpadDao = dao,
                            thinkrchiveApi = api,
                            backgroundDispatcher = Dispatchers.Default
                        )
                    }
                }
            )
        }
    }

    @AfterTest
    fun teardown() {
        unmockkAll()
        stopKoin()
    }

    @Test
    fun refreshThinkpadList_VerifyInsertAllThinkpadsIsCalled() = runTest {
        val responseData = TestData.thinkpadResponseList
        repo.refreshThinkpadList(responseData)
        verify { dao.insertAllThinkpads(responseData) }
    }

    @Test
    fun getAllThinkpads_ReturnsAllThinkpadsFromDbByDao() = runTest {
        val thinkpadDbList = TestData.ascendingOrderThinkpadsDbObject
        every { dao.getAllThinkpads() } returns flowOf(thinkpadDbList)
        val data = repo.getAllThinkpads()
        launch {
            data.test {
                verify { dao.getAllThinkpads() }
                val actual = expectMostRecentItem()
                val expect = thinkpadDbList.asDomainModel()
                assertEquals(expect, actual)
            }
        }
    }
}