package work.thinkrchive.v2.web_kt_js.ui.components

import csstype.*
import domain.Thinkpad
import emotion.react.css
import emotion.styled.styled
import react.FC
import react.Props
import react.dom.html.ReactHTML

external interface ThinkpadEntryProps : Props {
    var thinkpad: Thinkpad
    var selected: Boolean
    var onSelectThinkpad: (Thinkpad) -> Unit

    var percentage: Int
}

val ThinkpadEntry = FC<ThinkpadEntryProps> { props ->
    StyledCardDiv {
        onClick = {
            props.onSelectThinkpad(props.thinkpad)
        }

        ReactHTML.img { src = props.thinkpad.imageUrl }
        // Info
        StyledCardInfoDiv {
            ReactHTML.h2 { +props.thinkpad.model }
            ReactHTML.p { +props.thinkpad.releaseDate }
            // Progress Bar
            ProgressBar {
                progress = props.percentage
            }
        }
        ReactHTML.h2 { +"${props.percentage}%" }
    }
}

external interface ProgressBarProps : Props {
    var progress: Int
}

val ProgressBar = FC<ProgressBarProps> { props ->
    ReactHTML.div {

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

private val StyledCardDiv = ReactHTML.div.styled { _, _ ->
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

    ReactHTML.img {
        height = 6.rem
        width = 6.rem
    }

    ReactHTML.h2 {
        fontWeight = FontWeight.bold
    }
}

private val StyledCardInfoDiv = ReactHTML.div.styled { _, _ ->
    flex = number(1.0)
    padding = Padding(0.rem, 2.rem)
    display = Display.flex
    flexDirection = FlexDirection.column
    justifyContent = JustifyContent.spaceBetween
}