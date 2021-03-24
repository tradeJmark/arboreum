package ca.tradejmark.arboreum.cms.styling

import io.ktor.html.*
import kotlinx.html.DIV
import kotlinx.html.FlowContent
import kotlinx.html.HTML

interface BranchTemplate: Template<HTML> {
    val name: Placeholder<FlowContent>
    val shortDesc: Placeholder<FlowContent>
    val longDesc: Placeholder<FlowContent>
    val branches: PlaceholderList<FlowContent, SubbranchItemTemplate>
    val leaves: PlaceholderList<FlowContent, LeafItemTemplate>
}