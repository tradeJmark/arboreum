package ca.tradejmark.arboreum

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import io.ktor.application.*
import io.ktor.util.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class Arboreum(conf: Configuration) {
    private val mongoDB = KMongo.createClient(conf.mongoURL).coroutine.getDatabase(conf.database)
    private val parser = Parser.builder().build()
    private val renderer = HtmlRenderer.builder().build()
    val leafTemplate = conf.leafTemplate

    class Configuration {
        lateinit var mongoURL: String
        lateinit var database: String
        lateinit var leafTemplate: LeafTemplate
    }

    suspend fun leaves(branch: String): List<Leaf> {
        return mongoDB.getCollection<Leaf>(branch).find().toList()
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