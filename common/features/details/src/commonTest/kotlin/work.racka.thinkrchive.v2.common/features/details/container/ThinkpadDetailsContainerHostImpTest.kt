package work.racka.thinkrchive.v2.common.features.details.container

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
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
import states.details.ThinkpadDetailsSideEffect
import states.details.ThinkpadDetailsState
import work.racka.thinkrchive.v2.common.features.details.repository.DetailsRepository
import work.racka.thinkrchive.v2.common.features.details.util.Constants
import work.racka.thinkrchive.v2.common.features.details.util.TestData
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ThinkpadDetailsContainerHostImpTest : KoinTest {

    private val containerHost: ThinkpadDetailsContainerHostImpl by inject()

    @RelaxedMockK
    private lateinit var repo: DetailsRepository

    private val model: String = "some_thinkpad"

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockKAnnotations.init(this)
        startKoin {
            modules(
                module {
                    single {
                        ThinkpadDetailsContainerHostImpl(
                            repository = repo,
                            model = model,
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

    // Check for state emitted only. No side effects should be present here.
    @Test
    fun getThinkpad_WithThinkpadModelPresentInDatabase() = runTest {
        val thinkpad = TestData.thinkpad
        coEvery { repo.getThinkpad(model) } returns flowOf(thinkpad)
        val testSubject = containerHost.test()
        testSubject.runOnCreate()
        coVerify { repo.getThinkpad(model) }
        testSubject.assert(ThinkpadDetailsState.EmptyState) {
            states(
                { ThinkpadDetailsState.State(thinkpad) }
            )
        }
    }

    // Check if state is EmptyState and a DisplayErrorMsg side effect is posted
    @Test
    fun getThinkpad_WithThinkpadModelNotPresentInDatabase() = runTest {
        coEvery { repo.getThinkpad(model) } returns flowOf(null)
        val testSubject = containerHost.test()
        testSubject.runOnCreate()
        coVerify { repo.getThinkpad(model) }
        testSubject.assert(ThinkpadDetailsState.EmptyState) {
            states(
                { ThinkpadDetailsState.EmptyState }
            )

            postedSideEffects(
                ThinkpadDetailsSideEffect.DisplayErrorMsg(Constants.THINKPAD_NOT_FOUND)
            )
        }
    }

    @Test
    fun getThinkpad_WithThinkpadModelConstructorInHostAsNull() = runTest {
        val containerHost = ThinkpadDetailsContainerHostImpl(
            repository = repo,
            model = null, // This changes from the the default value by setup()
            scope = this
        )
        val testSubject = containerHost.test()
        testSubject.runOnCreate()
        testSubject.assert(ThinkpadDetailsState.EmptyState) {
            states(
                { ThinkpadDetailsState.EmptyState }
            )

            postedSideEffects(
                ThinkpadDetailsSideEffect.DisplayErrorMsg(Constants.THINKPAD_MODEL_NULL)
            )
        }
    }

    // When state is ThinkpadDetailsState.State it will produce a OpenPsrefLink side effect
    @Test
    fun openPsrefLink_WhenStateIsNotEmptyState() = runTest {
        val thinkpad = TestData.thinkpad
        val testSubject = containerHost.test(ThinkpadDetailsState.State(thinkpad))
        testSubject.testIntent {
            openPsrefLink()
        }
        testSubject.assert(ThinkpadDetailsState.State(thinkpad)) {
            postedSideEffects(
                ThinkpadDetailsSideEffect.OpenPsrefLink(thinkpad.psrefLink)
            )
        }
    }

    // When state is ThinkpadDetailsState.Empty it will produce a DisplayErrorMsg side effect
    @Test
    fun openPsrefLink_WhenStateIsEmptyState() = runTest {
        val thinkpad = TestData.thinkpad
        val testSubject = containerHost.test(ThinkpadDetailsState.EmptyState)
        testSubject.testIntent {
            openPsrefLink()
        }
        testSubject.assert(ThinkpadDetailsState.EmptyState) {
            postedSideEffects(
                ThinkpadDetailsSideEffect.DisplayErrorMsg(Constants.OPEN_LINK_ERROR)
            )
        }
    }
}