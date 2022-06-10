package work.thinkrchive.v2.web_kt_js.glass_website.components

import csstype.*
import emotion.styled.styled
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.img

val LinksComponent = FC<Props> {
    div {
        StyledLinkDiv {
            img { src = "./images/twitch.png" }
            h2 { +"Streams" }
        }
        StyledLinkDiv {
            img { src = "./images/steam.png" }
            h2 { +"Games" }
        }
        StyledLinkDiv {
            img { src = "./images/upcoming.png" }
            h2 { +"New" }
        }
        StyledLinkDiv {
            img { src = "./images/library.png" }
            h2 { +"Library" }
        }
    }
}

private val StyledLinkDiv = div.styled { _, _ ->
    display = Display.flex
    margin = Margin(1.rem, 0.rem)
    padding = Padding(1.rem, 2.rem)
    alignItems = AlignItems.center

    h2 {
        padding = Padding(0.rem, 1.rem)
    }

    img {
        height = 2.rem
        width = 2.rem
    }
}