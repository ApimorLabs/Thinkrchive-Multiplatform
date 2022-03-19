package work.racka.thinkrchive.v2.desktop

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import work.racka.thinkrchive.v2.common.integration.di.KoinMain

fun main() = application {

    KoinMain.initKoin()
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            Column {
                Text("Hello Desktop!")
            }
        }
    }
}