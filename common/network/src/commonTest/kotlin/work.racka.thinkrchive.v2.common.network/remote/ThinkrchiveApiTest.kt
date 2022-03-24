package work.racka.thinkrchive.v2.common.network.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import work.racka.thinkrchive.v2.common.network.di.Network.networkModules
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

// This test can take a while sometimes if the server is hibernating and needs
// to wake up. It can take up to 40s for the server to wake up
@OptIn(ExperimentalCoroutinesApi::class)
class ThinkrchiveApiTest : KoinTest {

    private val api: ThinkrchiveApi by inject()


    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin {
            networkModules(true)
        }
    }

    @Test
    fun testGetAllThinkpads() = runTest {
        val result = api.getThinkpads()
        print(result)
        assertTrue(result.isNotEmpty())
    }
}