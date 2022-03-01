package work.racka.thinkrchive.v2.common.settings

import com.russhwolf.settings.Settings

class SettingsRepository(
    private val settings: Settings
) {
    private object Keys {
        const val THEME_OPTION = "THEME_OPTION"
        const val SORT_OPTION = "SORT_OPTION"
    }

    fun saveThemeSettings(value: Int) {
        settings.putInt(
            key = Keys.THEME_OPTION,
            value = value
        )
    }

    fun readThemeSettings(): Int =
        settings.getInt(
            key = Keys.THEME_OPTION,
            defaultValue = -1
        )

    fun saveSortSettings(value: Int) {
        settings.putInt(
            key = Keys.SORT_OPTION,
            value = value
        )
    }

    fun readSortSettings(): Int =
        settings.getInt(
            key = Keys.SORT_OPTION,
            defaultValue = 0
        )
}