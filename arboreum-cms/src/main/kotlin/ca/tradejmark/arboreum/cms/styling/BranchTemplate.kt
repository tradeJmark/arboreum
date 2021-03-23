package ca.tradejmark.arboreum.cms.styling

import io.ktor.html.*
import kotlinx.html.DIV
import kotlinx.html.FlowContent
import kotlinx.html.HTML

interface BranchTemplate: Template<HTML> {
    val name: Placeholder<FlowContent>
    val description: Placeholder<FlowContent>
    val branches: PlaceholderList<FlowContent, SubbranchItemTemplate>
    val leaves: Placeholder<FlowContent>
}