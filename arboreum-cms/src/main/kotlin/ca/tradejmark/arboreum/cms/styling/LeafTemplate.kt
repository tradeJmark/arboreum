package ca.tradejmark.arboreum.cms.styling

import io.ktor.html.*
import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.TITLE

interface LeafTemplate: Template<HTML>{
    val title: Placeholder<FlowContent>
    val body: Placeholder<FlowContent>
}