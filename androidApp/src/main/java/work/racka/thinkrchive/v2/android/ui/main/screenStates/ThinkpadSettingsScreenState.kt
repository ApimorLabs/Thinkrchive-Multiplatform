package work.racka.thinkrchive.v2.android.ui.main.screenStates

sealed class ThinkpadSettingsScreenState {
    data class ThinkpadSettings(
        val themeOption: Int = -1,
        val sortOption: Int = 0
    ) : ThinkpadSettingsScreenState()

    companion object {
        val DefaultState = ThinkpadSettings()
    }
}