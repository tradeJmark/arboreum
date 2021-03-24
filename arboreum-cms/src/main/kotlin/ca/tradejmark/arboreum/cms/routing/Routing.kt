package ca.tradejmark.arboreum.cms.routing

import ca.tradejmark.arboreum.cms.Arboreum
import ca.tradejmark.arboreum.cms.schema.Branch
import ca.tradejmark.arboreum.cms.styling.LeafTemplate
import ca.tradejmark.arboreum.cms.schema.Leaf
import ca.tradejmark.arboreum.cms.schema.Tree
import ca.tradejmark.arboreum.cms.styling.BranchTemplate
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import kotlinx.html.div
import kotlinx.html.unsafe

suspend fun Route.arboreumBranch(name: String, mode: RoutingMode = RoutingMode.ByRoute, block: (Route.() -> Unit)? = null) {
    val arb = feature(Arboreum)
    val route = mode.getRoute(name, this)
    val branch = arb.branch(name)
    route.apply {
        get {
            branch(arb.branchTemplate, branch)
        }
        branch.leaves.forEach { leaf ->
            get(leaf.id) {
                leaf(arb.leafTemplate, leaf)
            }
        }
    }
    block?.let { it(route) }
}

suspend fun Route.arboreumTree(name: String, mode: RoutingMode = RoutingMode.ByRoute) {
    val arb = feature(Arboreum)
    arboreumTree(arb.tree(name), mode)
}

private suspend fun Route.arboreumTree(tree: Tree, mode: RoutingMode) {
    arboreumBranch(tree.name, mode)
    tree.branches.forEach { subtree ->
        arboreumTree(subtree, RoutingMode.ByRoute)
    }
}

suspend fun Route.arboreumSingleton(template: LeafTemplate, name: String, mode: RoutingMode = RoutingMode.ByRoute) {
    val arb = feature(Arboreum)
    val leaf = arb.singleton(name)
    val route = mode.getRoute(name, this)
    route.apply {
        get {
            leaf(template, leaf)
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.leaf(template: LeafTemplate, leaf: Leaf) {
    val arb = application.feature(Arboreum)
    call.respondHtmlTemplate(template) {
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

private suspend fun PipelineContext<Unit, ApplicationCall>.branch(template: BranchTemplate, branch: Branch) {
    val arb = application.feature(Arboreum)
    val subbranches = branch.subbranchNames.map { arb.branch(it) }
    call.respondHtmlTemplate(template) {
        name {
            +branch.name
        }
        shortDesc {
            div {
                unsafe {
                    +arb.render(branch.shortDesc)
                }
            }
        }
        longDesc {
            div {
                unsafe {
                    +arb.render(branch.longDesc)
                }
            }
        }
        subbranches.forEach { subbranch ->
            branches {
                name {
                    +subbranch.name
                }
                shortDesc {
                    div {
                        unsafe {
                            arb.render(subbranch.shortDesc)
                        }
                    }
                }
                href {
                    href = branch.id
                }
            }
        }
        branch.leaves.forEach { leaf ->
            leaves {
                title {
                    +leaf.title
                }
                blurb {
                    div {
                        unsafe {
                            arb.render(leaf.blurb)
                        }
                    }
                }
                href {
                    href = leaf.id
                }
            }
        }
    }
}

sealed class RoutingMode {
    abstract fun getRoute(routeName: String, parent: Route): Route
    class Literal(val path: String): RoutingMode() {
        override fun getRoute(routeName: String, parent: Route): Route {
            return parent.createRouteFromPath(path)
        }
    }
    object Flat: RoutingMode() {
        override fun getRoute(routeName: String, parent: Route): Route {
            return parent
        }
    }
    object ByRoute: RoutingMode() {
        override fun getRoute(routeName: String, parent: Route): Route {
            return parent.createRouteFromPath(routeName)
        }
    }
}