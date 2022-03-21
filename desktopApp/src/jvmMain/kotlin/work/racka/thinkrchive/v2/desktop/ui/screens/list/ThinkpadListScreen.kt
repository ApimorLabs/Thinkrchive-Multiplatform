package work.racka.thinkrchive.v2.desktop.ui.screens.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.Router
import org.koin.java.KoinJavaComponent.inject
import states.list.ThinkpadListSideEffect
import work.racka.thinkrchive.v2.common.features.thinkpad_list.viewmodel.ThinkpadListViewModel
import work.racka.thinkrchive.v2.desktop.ui.navigation.Component
import work.racka.thinkrchive.v2.desktop.ui.navigation.Configuration


class ThinkpadListScreen(
    private val componentContext: ComponentContext,
    private val onDetailsClicked: (model: String) -> Unit
) : Component, ComponentContext by componentContext {

    private val viewModel: ThinkpadListViewModel by inject(ThinkpadListViewModel::class.java)

    @Composable
    fun Screen(
        modifier: Modifier = Modifier,
        router: Router<Configuration, Any>
    ) {


    }

    @Composable
    override fun render() {
        val host = viewModel.hostDesktop
        val state by viewModel.hostDesktop.state.collectAsState()
        val sideEffect = viewModel.hostDesktop.sideEffect
            .collectAsState(initial = ThinkpadListSideEffect.Network())
            .value as ThinkpadListSideEffect.Network

        ThinkpadListScreenUI(
            thinkpadList = state.thinkpadList,
            networkLoading = sideEffect.isLoading,
            onSearch = { query ->
                host.getSortedThinkpadList(query)
            },
            onEntryClick = { thinkpad ->
                onDetailsClicked(thinkpad.model)
            },
            networkError = sideEffect.errorMsg,
            currentSortOption = state.sortOption,
            onSortOptionClicked = { sort ->
                host.sortSelected(sort)
            },
            onSettingsClicked = {
                //router.push(Configuration.ThinkpadSettingsScreen)
            },
            onAboutClicked = {
                //router.push(Configuration.ThinkpadAboutScreen)
            },
            onDonateClicked = {
                //router.push(Configuration.DonationScreen)
            }
        )
    }
}
