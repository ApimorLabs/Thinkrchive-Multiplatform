package work.racka.thinkrchive.v2.common.features.list.repository

import app.cash.turbine.test
import data.remote.response.ThinkpadResponse
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.SerializationException
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import util.NetworkError
import util.Resource
import work.racka.thinkrchive.v2.common.database.dao.ThinkpadDao
import work.racka.thinkrchive.v2.common.features.list.util.TestData
import work.racka.thinkrchive.v2.common.network.remote.ThinkrchiveApi
import kotlin.test.*

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
        val expect = TestData.ascendingOrderThinkpad
        every { dao.getAllThinkpads() } returns flowOf(thinkpadDbList)
        val data = repo.getAllThinkpads()
        launch {
            data.test {
                val actual = awaitItem()

                verify { dao.getAllThinkpads() }
                assertEquals(expect, actual)
                awaitComplete()
            }
        }
    }

    // Success from the API
    @Test
    fun getAllThinkpadsFromNetwork_EmitsLoadingResourceThenSuccessResourceWithData() = runTest {
        val responseList = TestData.thinkpadResponseList
        coEvery { api.getThinkpads() } returns responseList
        val data: Flow<Resource<List<ThinkpadResponse>, NetworkError>> =
            repo.getAllThinkpadsFromNetwork()
        launch {
            data.test {
                // Check if Resource.Loading is emitted
                assertTrue(awaitItem() is Resource.Loading)
                // Check if the Response list is returned
                assertEquals(responseList, awaitItem().data)
                awaitComplete()
            }
        }
    }

    // Failure from the API
    // Serialization Error
    @Test
    fun getAllThinkpadsFromNetwork_WhenInvalidDataReturned_EmitsLoadingResourceThenErrorResourceWithMsg() =
        runTest {
            val expectedErrorMsg = "Mangled data received"
            coEvery { api.getThinkpads() } throws SerializationException(expectedErrorMsg)
            val data: Flow<Resource<List<ThinkpadResponse>, NetworkError>> =
                repo.getAllThinkpadsFromNetwork()
            // Verify ap
            launch {
                data.test {
                    // Check if Resource.Loading is emitted
                    assertTrue(awaitItem() is Resource.Loading)

                    // Check if the Error Resource is returned with it's required message
                    val actualError = awaitItem()
                    val actualErrorMsg = actualError.message

                    assertTrue(actualError is Resource.Error)
                    assertNotNull(actualErrorMsg)
                    assertTrue(actualErrorMsg.contains(expectedErrorMsg))
                    assertEquals(actualError.errorCode, NetworkError.SerializationError)
                    awaitComplete()
                }
            }
        }

    // Network Error
    @Test
    fun getAllThinkpadsFromNetwork_WhenNoNetwork_EmitsLoadingResourceThenErrorResourceWithMsg() =
        runTest {
            val expectedErrorMsg = "Failure to Get List from Server"
            coEvery { api.getThinkpads() } throws Exception(expectedErrorMsg)
            val data: Flow<Resource<List<ThinkpadResponse>, NetworkError>> =
                repo.getAllThinkpadsFromNetwork()
            // Verify ap
            launch {
                data.test {
                    // Check if Resource.Loading is emitted
                    assertTrue(awaitItem() is Resource.Loading)

                    // Check if the Error Resource is returned with it's required message
                    val actualError = awaitItem()
                    val actualErrorMsg = actualError.message

                    assertTrue(actualError is Resource.Error)
                    assertNotNull(actualErrorMsg)
                    assertTrue(actualErrorMsg.contains(expectedErrorMsg))
                    assertEquals(actualError.errorCode, NetworkError.NoInternetError)
                    awaitComplete()
                }
            }
        }

    // TODO: Need to Test for ResponseException

    @Test
    fun getThinkpadsAlphaAscending_VerifyCallsTheDaoAndDataIsPassed() = runTest {
        val expected = TestData.ascendingOrderThinkpad
        val expectedAsDbObject = TestData.ascendingOrderThinkpadsDbObject
        val query = ""
        every { dao.getThinkpadsAlphaAscending(query) } returns flowOf(expectedAsDbObject)
        val data = repo.getThinkpadsAlphaAscending(query)
        launch {
            data.test {
                val actual = awaitItem()

                verify { dao.getThinkpadsAlphaAscending(query) }
                assertEquals(expected, actual)
                awaitComplete()
            }
        }
    }

    @Test
    fun getThinkpadsNewestFirst_VerifyCallsTheDaoAndDataIsPassed() = runTest {
        val expected = TestData.ascendingOrderThinkpad
        val expectedAsDbObject = TestData.ascendingOrderThinkpadsDbObject
        val query = ""
        every { dao.getThinkpadsNewestFirst(query) } returns flowOf(expectedAsDbObject)
        val data = repo.getThinkpadsNewestFirst(query)
        launch {
            data.test {
                val actual = awaitItem()

                verify { dao.getThinkpadsNewestFirst(query) }
                assertEquals(expected, actual)
                awaitComplete()
            }
        }
    }

    @Test
    fun getThinkpadsOldestFirst_VerifyCallsTheDaoAndDataIsPassed() = runTest {
        val expected = TestData.ascendingOrderThinkpad
        val expectedAsDbObject = TestData.ascendingOrderThinkpadsDbObject
        val query = ""
        every { dao.getThinkpadsOldestFirst(query) } returns flowOf(expectedAsDbObject)
        val data = repo.getThinkpadsOldestFirst(query)
        launch {
            data.test {
                val actual = awaitItem()

                verify { dao.getThinkpadsOldestFirst(query) }
                assertEquals(expected, actual)
                awaitComplete()
            }
        }
    }

    @Test
    fun getThinkpadsLowPriceFirst_VerifyCallsTheDaoAndDataIsPassed() = runTest {
        val expected = TestData.ascendingOrderThinkpad
        val expectedAsDbObject = TestData.ascendingOrderThinkpadsDbObject
        val query = ""
        every { dao.getThinkpadsLowPriceFirst(query) } returns flowOf(expectedAsDbObject)
        val data = repo.getThinkpadsLowPriceFirst(query)
        launch {
            data.test {
                val actual = expectMostRecentItem()

                verify { dao.getThinkpadsLowPriceFirst(query) }
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun getThinkpadsHighPriceFirst_VerifyCallsTheDaoAndDataIsPassed() = runTest {
        val expected = TestData.ascendingOrderThinkpad
        val expectedAsDbObject = TestData.ascendingOrderThinkpadsDbObject
        val query = ""
        every { dao.getThinkpadsHighPriceFirst(query) } returns flowOf(expectedAsDbObject)
        val data = repo.getThinkpadsHighPriceFirst(query)
        launch {
            data.test {
                val actual = awaitItem()

                verify { dao.getThinkpadsHighPriceFirst(query) }
                assertEquals(expected, actual)
                awaitComplete()
            }
        }
    }
}