package work.racka.thinkrchive.v2.android.ui.main.screenStates

import work.racka.thinkrchive.v2.common.database.model.Thinkpad

sealed class ThinkpadListScreenState {
    data class ThinkpadListScreen(
        val thinkpadList: List<Thinkpad> = listOf(),
        val networkLoading: Boolean = false,
        val networkError: String = "",
        val thinkpadSeriesList: List<String> = listOf(),
        var sortOption: Int = 0
    ) : ThinkpadListScreenState()

    companion object {
        val Empty = ThinkpadListScreen()
    }
}
