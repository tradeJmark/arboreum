package ca.tradejmark.arboreum.cms.styling

import io.ktor.html.*
import kotlinx.html.A
import kotlinx.html.FlowContent

abstract class LeafItemTemplate: Template<FlowContent> {
    val title: Placeholder<FlowContent> = Placeholder()
    val blurb: Placeholder<FlowContent> = Placeholder()
    val href: Placeholder<A> = Placeholder()
}