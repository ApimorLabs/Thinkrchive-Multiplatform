package work.thinkrchive.v2.web_kt_js.ui.components

import domain.Thinkpad
import react.FC
import react.Props
import react.memo

external interface ThinkpadListProps : Props {
    var thinkpadList: List<Thinkpad>
    var selectedThinkpad: Thinkpad?
    var onSelectThinkpad: (Thinkpad) -> Unit
}

val ThinkpadListComponent = memo(
    FC<ThinkpadListProps> { props ->
        for (thinkpadEntry in props.thinkpadList) {
            ThinkpadEntry {
                thinkpad = thinkpadEntry
                selected = thinkpadEntry == props.selectedThinkpad
                onSelectThinkpad = { props.onSelectThinkpad(it) }
                percentage = 50
            }
        }
    }
)