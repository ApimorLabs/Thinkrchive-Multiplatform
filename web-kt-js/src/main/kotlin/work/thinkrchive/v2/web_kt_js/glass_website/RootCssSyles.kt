package work.thinkrchive.v2.web_kt_js.glass_website

import csstype.*
import emotion.styled.styled
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.main
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section

val StyledMainContainer = main.styled { _, _ ->
    minHeight = 100.vh
    val color1 = stop(Color("#65dfc9"), 30.pct)
    val color2 = stop(Color("#6cdbeb"), 80.pct)
    background = linearGradient(45.deg, color1, color2)
    display = Display.flex
    alignItems = AlignItems.center
    justifyContent = JustifyContent.center
    fontFamily = FontFamily.sansSerif
    fontFace {
        fontFamily = "Poppins"
    }

    // Configure fonts and color
    h1 {
        color = Color("#426696")
        fontWeight = integer(600)
        fontSize = 2.rem
        opacity = number(.8)
    }
    h2 {
        color = Color("#658ec6")
        fontWeight = integer(500)
        fontSize = 1.rem
        opacity = number(.8)
    }
    h3 {
        color = Color("#426696")
        fontWeight = integer(600)
        fontSize = 1.rem
        opacity = number(.8)
    }
    p {
        color = Color("#658ec6")
        fontWeight = integer(500)
        fontSize = 1.rem
        opacity = number(.8)
    }

    input {
        placeholder {
            color = Color("#658ec6")
            fontWeight = integer(500)
            opacity = number(.8)
        }
    }
}

val StyledGlassSection = section.styled { _, _ ->
    background = NamedColor.white
    minHeight = 85.vh
    width = 80.vw
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
    backdropFilter = blur(2.rem)
    zIndex = integer(3)
    display = Display.flex
}

val StyledCircleDiv = div.styled { _, _ ->
    background = NamedColor.white
    val color1 = stop(
        rgba(255, 255, 255, .8),
        40.pct
    )
    val color2 = stop(
        rgba(255, 255, 255, .2),
        80.pct
    )
    background = linearGradient(135.deg, color1, color2)
    height = 15.rem
    width = 15.rem
    borderRadius = 50.pct
    position = Position.absolute
}