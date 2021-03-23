package ca.tradejmark.arboreum.cms.styling

import io.ktor.html.*
import kotlinx.html.A
import kotlinx.html.FlowContent

interface SubbranchItemTemplate: Template<FlowContent> {
    val href: Placeholder<A>
    val name: Placeholder<FlowContent>
    val description: Placeholder<FlowContent>
}