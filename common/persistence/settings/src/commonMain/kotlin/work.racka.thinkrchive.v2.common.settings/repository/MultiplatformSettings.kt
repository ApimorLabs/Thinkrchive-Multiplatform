package work.racka.thinkrchive.v2.common.settings.repository

import kotlinx.coroutines.flow.StateFlow

interface MultiplatformSettings {
    val themeFlow: StateFlow<Int>
    val sortFlow: StateFlow<Int>
    fun saveThemeSettings(value: Int)
    fun saveSortSettings(value: Int)
}