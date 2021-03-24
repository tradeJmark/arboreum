package ca.tradejmark.arboreum.cms.styling

import io.ktor.html.*
import kotlinx.html.FlowContent
import kotlinx.html.HTML

abstract class BranchTemplate: Template<HTML> {
    val name: Placeholder<FlowContent> = Placeholder()
    val shortDesc: Placeholder<FlowContent> = Placeholder()
    val longDesc: Placeholder<FlowContent> = Placeholder()
    val branches: PlaceholderList<FlowContent, SubbranchItemTemplate> = PlaceholderList()
    val leaves: PlaceholderList<FlowContent, LeafItemTemplate> = PlaceholderList()
}