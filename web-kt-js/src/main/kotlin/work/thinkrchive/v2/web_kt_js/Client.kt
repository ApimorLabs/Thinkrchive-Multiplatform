import kotlinx.browser.document
import react.create
import react.dom.client.createRoot
import work.thinkrchive.v2.web_kt_js.glass_website.GlassApMain

fun main() {
    val container = document.createElement("div")
    document.body!!.appendChild(container)

    createRoot(container).render(GlassApMain.create())
}