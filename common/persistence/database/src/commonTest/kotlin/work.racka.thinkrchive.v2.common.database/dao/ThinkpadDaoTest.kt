package work.racka.thinkrchive.v2.common.database.dao

import app.cash.turbine.test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import work.racka.thinkrchive.v2.common.database.di.TestPlatform
import work.racka.thinkrchive.v2.common.database.util.TestData
import work.racka.thinkrchive.v2.common.database.util.TestData.responseListToDbObjectList
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class ThinkpadDaoTest : KoinTest {

    private val dao: ThinkpadDao by inject()

    init {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @BeforeTest
    fun setup() {
        startKoin {
            modules(
                TestPlatform.testPlatformDatabaseModule(),
                module {
                    single<ThinkpadDao> {
                        ThinkpadDaoImpl(
                            coroutineScope = CoroutineScope(StandardTestDispatcher()),
                            thinkpadDatabaseWrapper = get()
                        )
                    }
                }
            )
        }
    }

    @AfterTest
    fun unloadKoin() {
        stopKoin()
    }

    @Test
    fun readAndWriteToDb_ProvidedArrayOfThinkpad_ShouldReadAllThinkpadsObjectsInDb() =
        runTest {
            dao.insertAllThinkpads(TestData.thinkpadResponseList)
            val dbObjects = dao.getAllThinkpads()
            launch {
                dbObjects.test {
                    val result = awaitItem()
                    assertTrue(result.isNotEmpty())
                }
            }
        }

    @Test
    fun getThinkpad_WhenThinkpadModelQueriedFound_ReturnsSingleThinkpadWithMatchingModel() =
        runTest {
            val thinkpadList = TestData.thinkpadResponseList
            val expected = thinkpadList
                .responseListToDbObjectList().first()
            val thinkpadModel = "Thinkpad T450"
            dao.insertAllThinkpads(thinkpadList)
            launch {
                dao.getThinkpad(thinkpadModel).test {
                    val actual = awaitItem()
                    assertNotNull(actual)
                    assertEquals(expected, actual)
                }
            }
        }

    @Test
    fun getThinkpad_WhenThinkpadModelQueriedNotFound_ReturnsNull() =
        runTest {
            val thinkpadList = TestData.thinkpadResponseList
            val wrongQuery = "Nothing To See"
            val expectedError = NullPointerException::class
            dao.insertAllThinkpads(thinkpadList)
            launch {
                // When an item is not found SQLDelight's Flow will be null
                dao.getThinkpad(wrongQuery).test {
                    val actualError = awaitError()::class
                    assertEquals(expectedError, actualError)
                }
            }
        }

    // We test for all the sorting functions used in our db
    // For getThinkpadsAlphaAscending()
    @Test
    fun searchForItemWithAscending_WhenQueryMatchesThinkpadModelName_ReturnsThinkpadListContainingModel() =
        runTest {
            val thinkpadList = TestData.thinkpadResponseList
            val expected = thinkpadList
                .responseListToDbObjectList().last() // Will be a Thinkpad T470
            val searchQuery = "T470"
            dao.insertAllThinkpads(thinkpadList)
            launch {
                // Ascending
                dao.getThinkpadsAlphaAscending(searchQuery).test {
                    val actualList = awaitItem()
                    val actual = actualList.first()
                    assertTrue(actualList.isNotEmpty())
                    assertEquals(expected, actual)
                }
            }
        }

    // For getThinkpadsNewestFirst()
    @Test
    fun searchForItemWithNewest_WhenQueryMatchesThinkpadModelName_ReturnsThinkpadListContainingModel() =
        runTest {
            val thinkpadList = TestData.thinkpadResponseList
            val expected = thinkpadList
                .responseListToDbObjectList().last() // Will be a Thinkpad T470
            val searchQuery = "T470"
            dao.insertAllThinkpads(thinkpadList)
            launch {
                // Newest First
                dao.getThinkpadsNewestFirst(searchQuery).test {
                    val actualList = awaitItem()
                    val actual = actualList.first()
                    assertTrue(actualList.isNotEmpty())
                    assertEquals(expected, actual)
                }
            }
        }

    // For getThinkpadsOldestFirst()
    @Test
    fun searchForItemWithOldest_WhenQueryMatchesThinkpadModelName_ReturnsThinkpadListContainingModel() =
        runTest {
            val thinkpadList = TestData.thinkpadResponseList
            val expected = thinkpadList
                .responseListToDbObjectList().last() // Will be a Thinkpad T470
            val searchQuery = "T470"
            dao.insertAllThinkpads(thinkpadList)
            launch {
                dao.getThinkpadsOldestFirst(searchQuery).test {
                    val actualList = awaitItem()
                    val actual = actualList.first()
                    assertTrue(actualList.isNotEmpty())
                    assertEquals(expected, actual)
                }
            }
        }

    // For getThinkpadsLowPriceFirst()
    @Test
    fun searchForItemWithLowPriceFirst_WhenQueryMatchesThinkpadModelName_ReturnsThinkpadListContainingModel() =
        runTest {
            val thinkpadList = TestData.thinkpadResponseList
            val expected = thinkpadList
                .responseListToDbObjectList().last() // Will be a Thinkpad T470
            val searchQuery = "T470"
            dao.insertAllThinkpads(thinkpadList)
            launch {
                dao.getThinkpadsLowPriceFirst(searchQuery).test {
                    val actualList = awaitItem()
                    val actual = actualList.first()
                    assertTrue(actualList.isNotEmpty())
                    assertEquals(expected, actual)
                }
            }
        }

    // For getThinkpadsHighPriceFirst()
    @Test
    fun searchForItemWithHighPriceFirst_WhenQueryMatchesThinkpadModelName_ReturnsThinkpadListContainingModel() =
        runTest {
            val thinkpadList = TestData.thinkpadResponseList
            val expected = thinkpadList
                .responseListToDbObjectList().last() // Will be a Thinkpad T470
            val searchQuery = "T470"
            dao.insertAllThinkpads(thinkpadList)
            launch {
                dao.getThinkpadsHighPriceFirst(searchQuery).test {
                    val actualList = awaitItem()
                    val actual = actualList.first()
                    assertTrue(actualList.isNotEmpty())
                    assertEquals(expected, actual)
                }
            }
        }

    // We can expect all of them to return an empty list.
    // We'll only test for the ascending function for now.
    @Test
    fun searchForItemInDb_WhenQueryDoesNotMatchThinkpadModelName_ReturnsEmptyList() =
        runTest {
            val thinkpadList = TestData.thinkpadResponseList
            val searchQuery = "Dell XPS 9360"
            dao.insertAllThinkpads(thinkpadList)
            launch {
                dao.getThinkpadsHighPriceFirst(searchQuery).test {
                    val actual = awaitItem()
                    assertTrue(actual.isEmpty())
                }
            }
        }

    @Test
    fun getThinkpadsAlphaAscending_ReturnsThinkpadsSortedByAlphabeticalAscendingOrderOfModelName() =
        runTest {
            val thinkpadList = TestData.thinkpadResponseList
            val expected = thinkpadList.sortedBy { it.model }
                .responseListToDbObjectList()
            dao.insertAllThinkpads(thinkpadList)
            launch {
                // Empty string in thinkpadModel param to return all Thinkpads in the DB
                dao.getThinkpadsAlphaAscending("").test {
                    val actual = awaitItem()
                    assertEquals(expected, actual)
                    assertTrue(actual.first().model < actual.last().model)
                }
            }
        }

    @Test
    fun getThinkpadsNewestFirst_ReturnsThinkpadsSortedByNewestReleaseDateFirst() =
        runTest {
            val thinkpadList = TestData.thinkpadResponseList
            // We want items at the beginning to have a higher order lexicographically
            // than the ones at the bottom
            val expected = thinkpadList.sortedByDescending { it.releaseDate }
                .responseListToDbObjectList()
            dao.insertAllThinkpads(thinkpadList)
            launch {
                // Empty string in thinkpadModel param to return all Thinkpads in the DB
                dao.getThinkpadsNewestFirst("").test {
                    val actual = awaitItem()
                    assertEquals(expected, actual)
                    // Item at the top will have a higher date order lexicographically
                    // than the one at the bottom
                    assertTrue(actual.first().releaseDate > actual.last().releaseDate)
                }
            }
        }

    @Test
    fun getThinkpadsOldestFirst_ReturnsThinkpadsSortedByOldestReleaseDateFirst() =
        runTest {
            val thinkpadList = TestData.thinkpadResponseList
            // We want items at the beginning to have a lower order lexicographically
            // than the ones at the bottom
            val expected = thinkpadList.sortedBy { it.releaseDate }
                .responseListToDbObjectList()
            dao.insertAllThinkpads(thinkpadList)
            launch {
                // Empty string in thinkpadModel param to return all Thinkpads in the DB
                dao.getThinkpadsOldestFirst("").test {
                    val actual = awaitItem()
                    assertEquals(expected, actual)
                    // Item at the top will have a lower date order lexicographically
                    // than the one at the bottom
                    assertTrue(actual.first().releaseDate < actual.last().releaseDate)
                }
            }
        }

    @Test
    fun getThinkpadsLowPriceFirst_ReturnsThinkpadsSortedByLowestPriceFirst() =
        runTest {
            val thinkpadList = TestData.thinkpadResponseList
            // We want items at the beginning to have a lower number
            // than the ones at the bottom
            val expected = thinkpadList.sortedBy { it.marketPriceStart }
                .responseListToDbObjectList()
            dao.insertAllThinkpads(thinkpadList)
            launch {
                // Empty string in thinkpadModel param to return all Thinkpads in the DB
                dao.getThinkpadsLowPriceFirst("").test {
                    val actual = awaitItem()
                    assertEquals(expected, actual)
                    assertTrue(actual.first().marketPriceStart < actual.last().marketPriceStart)
                }
            }
        }

    @Test
    fun getThinkpadsHighPriceFirst_ReturnsThinkpadsSortedByHighestPriceFirst() =
        runTest {
            val thinkpadList = TestData.thinkpadResponseList
            // We want items at the beginning to have a higher number
            // than the ones at the bottom
            val expected = thinkpadList.sortedByDescending { it.marketPriceStart }
                .responseListToDbObjectList()
            dao.insertAllThinkpads(thinkpadList)
            launch {
                // Empty string in thinkpadModel param to return all Thinkpads in the DB
                dao.getThinkpadsHighPriceFirst("").test {
                    val actual = awaitItem()
                    assertEquals(expected, actual)
                    assertTrue(actual.first().marketPriceStart > actual.last().marketPriceStart)
                }
            }
        }
}