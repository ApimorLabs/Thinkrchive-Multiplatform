package work.thinkrchive.v2.web_kt_js.glass_website.panes

import csstype.*
import domain.Thinkpad
import emotion.styled.styled
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.component.get
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import util.asThinkpad
import work.racka.thinkrchive.v2.common.network.remote.ThinkrchiveApi
import work.thinkrchive.v2.web_kt_js.AppDependenciesContext
import work.thinkrchive.v2.web_kt_js.ui.components.ThinkpadListComponent

val GamesPane = FC<Props> {
    var data: List<Thinkpad> by useState(emptyList())
    var selectedModel: Thinkpad? by useState(null)

    val appDependencies = useContext(AppDependenciesContext)

    useEffect {
        val scope = MainScope()
        val api: ThinkrchiveApi = appDependencies.get()
        scope.launch {
            try {
                val result = api.getThinkpads().map { it.asThinkpad() }
                println(result)
                data = result
            } catch (e: Exception) {
                println("Network error")
            }
        }

        cleanup { scope.cancel() }
    }

    StyledGamesPaneDiv {
        // Status
        div {
            h1 { +"Active Games" }
            input {
                type = InputType.text
                placeholder = "Enter Game name"
            }
        }
        // Cards
        ThinkpadListComponent {
            thinkpadList = data
            selectedThinkpad = selectedModel
            onSelectThinkpad = { }
        }
    }
}

private val StyledGamesPaneDiv = div.styled { _, _ ->
    flex = number(2.0)
    margin = Margin(1.rem, 2.rem)
    display = Display.flex
    flexDirection = FlexDirection.column
    justifyContent = JustifyContent.spaceEvenly

    h1 {
        paddingBottom = 0.8.rem
    }

    input {
        val color1 = stop(
            rgba(255, 255, 255, .7),
            30.vw
        )
        val color2 = stop(
            rgba(255, 255, 255, .3),
            80.vw
        )
        background = linearGradient(135.deg, color1, color2)
        border = None.none
        borderRadius = 1.rem
        width = 60.pct
        padding = 0.8.rem
    }
}