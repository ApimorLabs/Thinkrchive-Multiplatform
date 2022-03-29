package states.list

import util.NetworkError

sealed class ThinkpadListSideEffect {
    object NoSideEffect : ThinkpadListSideEffect()

    data class ShowNetworkErrorSnackbar(
        val msg: String,
        val networkError: NetworkError
    ) : ThinkpadListSideEffect()
}
