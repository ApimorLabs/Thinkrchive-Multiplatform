package work.racka.thinkrchive.v2.common.database.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import work.racka.thinkrchive.v2.common.database.di.Database.databaseModules
import work.racka.thinkrchive.v2.common.database.di.TestPlatform
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ThinkpadDaoTest : KoinTest {

    private val dao: ThinkpadDao by inject()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin {
            TestPlatform.testPlatformDatabaseModule()
            databaseModules()
        }
    }

    @Test
    fun
}