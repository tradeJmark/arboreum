package ca.tradejmark.arboreum.cms.styling

import io.ktor.html.*
import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.TITLE

abstract class LeafTemplate: Template<HTML>{
    val title: Placeholder<FlowContent> = Placeholder()
    val blurb: Placeholder<FlowContent> = Placeholder()
    val body: Placeholder<FlowContent> = Placeholder()
}