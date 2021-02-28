package ca.tradejmark.arboreum.routing

import ca.tradejmark.arboreum.Arboreum
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.div
import kotlinx.html.unsafe

suspend fun Route.arboreumBranch(name: String, mode: RoutingMode = RoutingMode.ByRoute, block: Route.() -> Unit) {
    val arb = feature(Arboreum)
    val route = when (mode) {
        is RoutingMode.Literal -> createRouteFromPath(mode.path)
        is RoutingMode.Flat -> this
        is RoutingMode.ByRoute -> createRouteFromPath(name)
    }
    arb.leaves(name).forEach { leaf ->
        route.apply {
            get(leaf.id) {
                call.respondHtmlTemplate(arb.leafTemplate) {
                    title {
                        +leaf.title
                    }
                    body {
                        div {
                            unsafe {
                                +arb.render(leaf.body)
                            }
                        }
                    }
                }
            }
        }
    }
    block(route)
}

sealed class RoutingMode {
    class Literal(val path: String): RoutingMode()
    object Flat: RoutingMode()
    object ByRoute: RoutingMode()
}