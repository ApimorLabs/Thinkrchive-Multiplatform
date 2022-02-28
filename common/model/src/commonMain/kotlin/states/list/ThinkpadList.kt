package states.list

import domain.Thinkpad

sealed class ThinkpadList {
    data class ThinkpadListState(
        val thinkpadList: List<Thinkpad> = listOf(),
        val sortOption: Int = 0
    ) : ThinkpadList()

    companion object {
        val EmptyState = ThinkpadListState()
    }
}
