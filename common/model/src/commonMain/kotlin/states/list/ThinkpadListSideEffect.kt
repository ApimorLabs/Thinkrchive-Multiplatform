package states.list

sealed class ThinkpadListSideEffect {
    data class Network(
        val isLoading: Boolean = false,
        val errorMsg: String = ""
    ) : ThinkpadListSideEffect()
}
