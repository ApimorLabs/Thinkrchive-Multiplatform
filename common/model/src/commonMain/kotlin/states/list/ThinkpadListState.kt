package states.list

import domain.Thinkpad

sealed class ThinkpadListState {
    data class State(
        val thinkpadList: List<Thinkpad> = listOf(),
        val sortOption: Int = 0,
        val networkLoading: Boolean = false
    ) : ThinkpadListState()

    companion object {
        val EmptyState = State()
    }
}
