package work.thinkrchive.v2.web_kt_js.glass_website.components

import csstype.*
import emotion.styled.styled
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.img

val ProComponent = FC<Props> {
    StyledProComponentDiv {
        h2 { +"Join Pro for free Games" }
        img { src = "./images/controller.png" }
    }
}

private val StyledProComponentDiv = div.styled { _, _ ->
    val color1 = stop(Color("#65dfc9"), 30.pct)
    val color2 = stop(Color("#6cdbeb"), 80.pct)
    background = linearGradient(45.deg, color1, color2)
    borderRadius = 2.rem
    color = NamedColor.white
    padding = 1.rem
    position = Position.relative

    img {
        position = Position.absolute
        top = (-20).pct
        right = 7.pct
        height = 7.rem
        width = 7.rem
    }

    h2 {
        width = 40.pct
        color = NamedColor.white
        fontWeight = integer(600)
    }
}