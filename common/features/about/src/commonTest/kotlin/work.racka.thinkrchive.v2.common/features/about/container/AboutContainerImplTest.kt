package work.racka.thinkrchive.v2.common.features.about.container

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.orbitmvi.orbit.test
import states.about.AboutSideEffect
import states.about.AboutState
import work.racka.thinkrchive.v2.common.features.about.data.AboutData
import work.racka.thinkrchive.v2.common.features.about.util.Constants
import work.racka.thinkrchive.v2.common.features.about.util.TestData
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AboutContainerImplTest : KoinTest {

    private val containerHost: AboutContainerImpl by inject()

    @RelaxedMockK
    private lateinit var aboutData: AboutData

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin {
            modules(
                module {
                    single {
                        AboutContainerImpl(
                            aboutData = aboutData,
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
    fun onContainerCreation_WhenContainerCreatedAndUpdateFound_ProducesStateWithASideEffectWhenUpdateFound() =
        runTest {
            val data = TestData.appAboutData

            // Mocking
            every { aboutData.getAppAboutData() } returns data
            every { aboutData.hasUpdates() } returns true

            // Testing
            val testSubject = containerHost.test()
            testSubject.runOnCreate()
            testSubject.assert(AboutState.EmptyState) {
                states(
                    { copy(appAbout = data, hasUpdates = true) }
                )

                postedSideEffects(
                    AboutSideEffect.UpdateFound(Constants.UPDATE_FOUND)
                )
            }
        }

    @Test
    fun onContainerCreation_WhenContainerCreatedAndUpdateNotFound_ProducesState() =
        runTest {
            val data = TestData.appAboutData

            // Mocking
            every { aboutData.getAppAboutData() } returns data
            every { aboutData.hasUpdates() } returns false

            // Testing
            val testSubject = containerHost.test()
            testSubject.runOnCreate()

            verify { aboutData.getAppAboutData() }
            verify { aboutData.hasUpdates() }
            testSubject.assert(AboutState.EmptyState) {
                states(
                    { copy(appAbout = data, hasUpdates = false) }
                )
            }
        }

    @Test
    fun update_WhenUpdateNotFound_ProducesUpdateNotFoundSideEffect() = runTest {
        // Mocking
        every { aboutData.hasUpdates() } returns false

        // Testing
        val testSubject = containerHost.test()
        testSubject.testIntent { update() }

        verify { aboutData.hasUpdates() }
        testSubject.assert(AboutState.EmptyState) {
            states(
                { copy(hasUpdates = false) }
            )

            postedSideEffects(
                AboutSideEffect.UpdateNotFound(Constants.UPDATE_NOT_FOUND)
            )
        }
    }

    @Test
    fun update_WhenUpdateFound_ProducesUpdateSideEffect() = runTest {
        // Mocking
        every { aboutData.hasUpdates() } returns true

        // Testing
        val testSubject = containerHost.test()
        testSubject.testIntent { update() }

        verify { aboutData.hasUpdates() }
        testSubject.assert(AboutState.EmptyState) {
            states(
                { copy(hasUpdates = true) }
            )

            postedSideEffects(
                AboutSideEffect.Update
            )
        }
    }
}