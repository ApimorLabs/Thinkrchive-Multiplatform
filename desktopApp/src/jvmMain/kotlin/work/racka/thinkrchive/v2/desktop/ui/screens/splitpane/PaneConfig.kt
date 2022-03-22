package work.racka.thinkrchive.v2.desktop.ui.screens.splitpane

sealed class PaneConfig {
    object Blank : PaneConfig()
    data class Details(val model: String) : PaneConfig()
}
