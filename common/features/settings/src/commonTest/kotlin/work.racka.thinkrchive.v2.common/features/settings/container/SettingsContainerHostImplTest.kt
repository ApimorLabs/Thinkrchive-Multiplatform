package work.racka.thinkrchive.v2.common.features.settings.container

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import states.settings.ThinkpadSettingsSideEffect
import states.settings.ThinkpadSettingsState
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsContainerHostImplTest : KoinTest {

    private val containerHost: SettingsContainerHostImpl by inject()

    @RelaxedMockK
    lateinit var settingsRepo: MultiplatformSettings

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin {
            modules(
                module {
                    single {
                        SettingsContainerHostImpl(
                            settings = settingsRepo,
                            scope = CoroutineScope(Dispatchers.Default)
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
    fun onContainerCreation_WhenReadThemeSettings_ProducesStateWithCorrectThemeValue() =
        runTest {
            val expectedThemeValue = 2
            // Mocking
            every { settingsRepo.themeFlow } returns flowOf(expectedThemeValue)

            //Testing
            val testSubject =
                containerHost.test() // DefaultState for ThinkpadSettingsState is provided
            testSubject.runOnCreate()

            verify { settingsRepo.themeFlow }
            testSubject.assert(ThinkpadSettingsState.DefaultState) {
                states(
                    { copy(themeValue = expectedThemeValue) }
                )
            }
        }

    @Test
    fun onContainerCreation_WhenReadSortSettings_ProducesStateWithCorrectSortValue() =
        runTest {
            val expectedSortValue = 1
            // Mocking
            every { settingsRepo.sortFlow } returns flowOf(expectedSortValue)

            //Testing
            val testSubject =
                containerHost.test() // DefaultState for ThinkpadSettingsState is provided
            testSubject.runOnCreate()

            verify { settingsRepo.sortFlow }
            testSubject.assert(ThinkpadSettingsState.DefaultState) {
                states(
                    { copy(sortValue = expectedSortValue) }
                )
            }
        }

    @Test
    fun saveThemeSettings_WhenSaveThemeSettings_ProducesSideEffectWithCorrectThemeValue() =
        runTest {
            val expectedThemeValue1 = 1
            val expectedThemeValue2 = 2

            //Testing
            val testSubject =
                containerHost.test() // DefaultState for ThinkpadSettingsState is provided

            // Save first themeValue
            testSubject.testIntent { saveThemeSettings(expectedThemeValue1) }
            // Save 2nd themeValue
            testSubject.testIntent { saveThemeSettings(expectedThemeValue2) }

            testSubject.assert(ThinkpadSettingsState.DefaultState) {
                postedSideEffects(
                    ThinkpadSettingsSideEffect.ApplyThemeOption(expectedThemeValue1),
                    ThinkpadSettingsSideEffect.ApplyThemeOption(expectedThemeValue2)
                )
            }
        }

    @Test
    fun saveSortSettings_WhenSaveSortSettings_ProducesSideEffectWithCorrectSortValue() =
        runTest {
            val expectedSortValue1 = 1
            val expectedSortValue2 = 2

            //Testing
            val testSubject =
                containerHost.test() // DefaultState for ThinkpadSettingsState is provided

            testSubject.testIntent { saveSortSettings(expectedSortValue1) }
            // Save 2nd sortValue
            testSubject.testIntent { saveSortSettings(expectedSortValue2) }

            //verify { settingsRepo.sortFlow }
            testSubject.assert(ThinkpadSettingsState.DefaultState) {
                postedSideEffects(
                    ThinkpadSettingsSideEffect.ApplySortOption(expectedSortValue1),
                    ThinkpadSettingsSideEffect.ApplySortOption(expectedSortValue2)
                )
            }
        }
}