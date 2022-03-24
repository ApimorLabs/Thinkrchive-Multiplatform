package work.racka.thinkrchive.v2.common.settings.repository

import com.russhwolf.settings.MockSettings
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SettingsRepositoryTest : KoinTest {

    private val repo: SettingsRepository by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(
                module {
                    single<SettingsRepository> {
                        val settings = MockSettings()
                        SettingsRepositoryImpl(settings)
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
    fun testDefaultValueForReadThemeSettings_ShouldReturn_Negative_1() {
        val expect = -1
        val actual = repo.readThemeSettings()
        assertEquals(expect, actual)
    }

    @Test
    fun testDefaultValueForReadSortSettings_ShouldReturn_0() {
        val expect = 0
        val actual = repo.readSortSettings()
        assertEquals(expect, actual)
    }

    @Test
    fun saveThemeSettingsAndReadThemeSettings_ShouldReturnSameValueOnRead() {
        val expect = 2
        repo.saveThemeSettings(expect)
        val actual = repo.readThemeSettings()
        assertEquals(expect, actual)
    }

    @Test
    fun saveSortSettingsAndReadSortSettings_ShouldReturnSameValueOnRead() {
        val expect = 8
        repo.saveThemeSettings(expect)
        val actual = repo.readThemeSettings()
        assertEquals(expect, actual)
    }
}