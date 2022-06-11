package work.thinkrchive.v2.web_kt_js

import kotlinx.browser.document
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import react.create
import react.createContext
import react.dom.client.createRoot
import work.racka.thinkrchive.v2.common.network.di.Network
import work.thinkrchive.v2.web_kt_js.glass_website.GlassApMain

val AppDependenciesContext = createContext(AppDependencies)

object AppDependencies : KoinComponent {
    init {
        startKoin {
            with(Network) { networkModules() }
        }
    }
}

fun main() {
    val container = document.createElement("div")
    document.body!!.appendChild(container)

    createRoot(container).render(GlassApMain.create())
}