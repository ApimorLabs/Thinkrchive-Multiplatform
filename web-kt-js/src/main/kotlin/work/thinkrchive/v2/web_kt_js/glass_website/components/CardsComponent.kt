package work.thinkrchive.v2.web_kt_js.glass_website.components

import csstype.*
import emotion.react.css
import emotion.styled.styled
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p

external interface CardProps : Props {
    var imgSrc: String
    var percentage: Int
    var gameName: String
    var gameVersion: String
}

val CardComponent = FC<CardProps> { props ->
    StyledCardDiv {
        img { src = props.imgSrc }
        // Info
        StyledCardInfoDiv {
            h2 { +props.gameName }
            p { +props.gameVersion }
            // Progress Bar
            ProgressBar {
                progress = props.percentage
            }
        }
        h2 { +"${props.percentage}%" }
    }
}

external interface ProgressBarProps : Props {
    var progress: Int
}

val ProgressBar = FC<ProgressBarProps> { props ->
    div {

        css {
            background = rgb(236, 236, 236)
            width = 100.pct
            height = 25.pct
            borderRadius = 1.rem
            position = Position.relative

            after {
                content = Content.closeQuote
                val color1 = stop(Color("#65dfc9"), 30.pct)
                val color2 = stop(Color("#6cdbeb"), 80.pct)
                background = linearGradient(45.deg, color1, color2)
                width = props.progress.pct
                height = 100.pct
                borderRadius = 1.rem
                position = Position.absolute
            }
        }

    }
}

val StyledCardDiv = div.styled { _, _ ->
    display = Display.flex
    val color1 = stop(
        rgba(255, 255, 255, 1.0),
        30.vw
    )
    val color2 = stop(
        rgba(255, 255, 255, .8),
        80.vw
    )
    background = linearGradient(135.deg, color1, color2)
    borderRadius = 1.rem
    margin = Margin(.5.rem, 0.rem)
    padding = 1.rem
    boxShadow = BoxShadow(5.px, 5.px, 15.px, rgba(122, 122, 122, .2))
    justifyContent = JustifyContent.spaceBetween

    img {
        height = 6.rem
        width = 6.rem
    }

    h2 {
        fontWeight = FontWeight.bold
    }
}

val StyledCardInfoDiv = div.styled { _, _ ->
    flex = number(1.0)
    padding = Padding(0.rem, 2.rem)
    display = Display.flex
    flexDirection = FlexDirection.column
    justifyContent = JustifyContent.spaceBetween
}