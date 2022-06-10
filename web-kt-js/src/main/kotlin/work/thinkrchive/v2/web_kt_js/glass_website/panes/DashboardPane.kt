package work.thinkrchive.v2.web_kt_js.glass_website.panes

import csstype.*
import emotion.styled.styled
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import work.thinkrchive.v2.web_kt_js.glass_website.components.LinksComponent
import work.thinkrchive.v2.web_kt_js.glass_website.components.ProComponent
import work.thinkrchive.v2.web_kt_js.glass_website.components.UserComponent

val DashboardPane = FC<Props> {
    StyledDashboardPaneDiv {
        UserComponent()
        LinksComponent()
        ProComponent()
    }
}

private val StyledDashboardPaneDiv = div.styled { _, _ ->
    flex = number(1.0)
    display = Display.flex
    flexDirection = FlexDirection.column
    alignItems = AlignItems.center
    justifyContent = JustifyContent.spaceEvenly
    textAlign = TextAlign.center
    val color1 = stop(
        rgba(255, 255, 255, .7),
        30.vw
    )
    val color2 = stop(
        rgba(255, 255, 255, .3),
        80.vw
    )
    background = linearGradient(135.deg, color1, color2)
    borderRadius = 2.rem
    padding = Padding(2.rem, 1.rem)
}