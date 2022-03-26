package work.racka.thinkrchive.v2.common.settings.repository

import kotlinx.coroutines.flow.Flow

interface MultiplatformSettings {
    val themeFlow: Flow<Int>
    val sortFlow: Flow<Int>
    fun saveThemeSettings(value: Int)
    fun saveSortSettings(value: Int)
}