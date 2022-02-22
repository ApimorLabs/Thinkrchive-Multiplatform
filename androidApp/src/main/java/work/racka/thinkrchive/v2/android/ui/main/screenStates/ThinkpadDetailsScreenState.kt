package work.racka.thinkrchive.v2.android.ui.main.screenStates

import work.racka.thinkrchive.v2.common.database.model.Thinkpad

sealed class ThinkpadDetailsScreenState {
    data class ThinkpadDetail(val thinkpad: Thinkpad) : ThinkpadDetailsScreenState()
    object EmptyState : ThinkpadDetailsScreenState()
}