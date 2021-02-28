package ca.tradejmark.arboreum

import io.ktor.html.*
import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.TITLE

interface LeafTemplate: Template<HTML>{
    val title: Placeholder<TITLE>
    val body: Placeholder<FlowContent>
}