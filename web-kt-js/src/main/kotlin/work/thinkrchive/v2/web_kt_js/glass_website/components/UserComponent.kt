package work.thinkrchive.v2.web_kt_js.glass_website.components

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p

val UserComponent = FC<Props> {
    div {
        img { src = "./images/avatar.png" }
        h3 { +"John Legend" }
        p { +"Pro Member" }
    }
}