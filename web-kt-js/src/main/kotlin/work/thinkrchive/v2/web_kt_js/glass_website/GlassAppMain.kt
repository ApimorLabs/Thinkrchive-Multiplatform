package work.thinkrchive.v2.web_kt_js.glass_website

import csstype.integer
import csstype.pct
import emotion.react.css
import react.FC
import react.Props
import work.thinkrchive.v2.web_kt_js.glass_website.panes.DashboardPane
import work.thinkrchive.v2.web_kt_js.glass_website.panes.GamesPane

val GlassApMain = FC<Props> {
    StyledMainContainer {
        //Glass
        StyledGlassSection {
            // Dashboard & Games Panes
            DashboardPane()
            GamesPane()
        }
    }

    // Circle1
    StyledCircleDiv {
        css {
            zIndex = integer(1)
            top = 2.pct
            right = 5.pct
        }
    }

    // Circle2
    StyledCircleDiv {
        css {
            zIndex = integer(2)
            bottom = 2.pct
            left = 5.pct
        }
    }
}

