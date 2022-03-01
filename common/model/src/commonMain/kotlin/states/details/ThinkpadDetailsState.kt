package states.details

import domain.Thinkpad

sealed class ThinkpadDetailsState {
    data class State(val thinkpad: Thinkpad) : ThinkpadDetailsState()
    object EmptyState : ThinkpadDetailsState()
}
