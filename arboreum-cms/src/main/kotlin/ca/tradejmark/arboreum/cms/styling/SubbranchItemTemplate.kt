package ca.tradejmark.arboreum.cms.styling

import io.ktor.html.*
import kotlinx.html.A
import kotlinx.html.FlowContent

abstract class SubbranchItemTemplate: Template<FlowContent> {
    val href: Placeholder<A> = Placeholder()
    val name: Placeholder<FlowContent> = Placeholder()
    val shortDesc: Placeholder<FlowContent> = Placeholder()
}