package ca.tradejmark.arboreum.cms

import ca.tradejmark.arboreum.cms.schema.Tree
import ca.tradejmark.arboreum.cms.schema.Branch
import ca.tradejmark.arboreum.cms.schema.Leaf
import ca.tradejmark.arboreum.cms.styling.BranchTemplate
import ca.tradejmark.arboreum.cms.styling.LeafTemplate
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.util.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class Arboreum(conf: Configuration) {
    private val mongoDB = KMongo.createClient(conf.mongoURL).coroutine.getDatabase(conf.database)
    private val parser = Parser.builder().build()
    private val renderer = HtmlRenderer.builder().build()
    val leafTemplate = conf.leafTemplate
    val branchTemplate = conf.branchTemplate

    class Configuration {
        lateinit var mongoURL: String
        lateinit var database: String
        lateinit var leafTemplate: LeafTemplate
        lateinit var branchTemplate: BranchTemplate
    }

    suspend fun branch(name: String): Branch {
        val branches = mongoDB.getCollection<Branch>("_branches")
        val b = branches.findOne(Branch::name eq name) ?: throw NotFoundException("No branch exists named $name.")
        val leaves = mongoDB.getCollection<Leaf>(name).find().toList()
        b.leaves = leaves
        return b
    }

    suspend fun tree(root: String): Tree {
        val branches = mongoDB.getCollection<Branch>("_branches")
        val rootBranch = branches.findOne(Branch::name eq root) ?: throw NotFoundException("No branch named $root.")
        return Tree(rootBranch.name, rootBranch.subbranchNames.map { tree(it) })
    }

    suspend fun singleton(name: String): Leaf {
        return mongoDB.getCollection<Leaf>("_singletons").findOne(Leaf::id eq name) ?: throw NotFoundException("No singleton named $name.")
    }

    fun render(markdown: String): String {
        val node = parser.parse(markdown)
        return renderer.render(node)
    }

    companion object Feature: ApplicationFeature<ApplicationCallPipeline, Configuration, Arboreum> {
        override val key = AttributeKey<Arboreum>("Arboreum")

        override fun install(pipeline: ApplicationCallPipeline, configure: Configuration.() -> Unit): Arboreum {
            val conf = Configuration().apply(configure)

            return Arboreum(conf)
        }
    }
}