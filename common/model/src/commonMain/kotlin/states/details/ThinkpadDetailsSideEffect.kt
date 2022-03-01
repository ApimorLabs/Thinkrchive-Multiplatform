package states.details

sealed class ThinkpadDetailsSideEffect {
    data class OpenPsrefLink(val link: String) : ThinkpadDetailsSideEffect()
    data class DisplayErrorMsg(val message: String) : ThinkpadDetailsSideEffect()
    object EmptySideEffect : ThinkpadDetailsSideEffect()
}
