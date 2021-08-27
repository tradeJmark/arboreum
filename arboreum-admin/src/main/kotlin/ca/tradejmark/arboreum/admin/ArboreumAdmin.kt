package ca.tradejmark.arboreum.admin

import io.ktor.application.*
import io.ktor.util.*
import io.ktor.webjars.*

class ArboreumAdmin(conf: Configuration) {
    val webjarPath = conf.webjarPath ?: WEBJAR_DEFAULT

    class Configuration {
        var webjarPath: String? = null
    }

    companion object Feature: ApplicationFeature<ApplicationCallPipeline, Configuration, ArboreumAdmin> {
        private const val WEBJAR_DEFAULT = "webjars"
        override val key = AttributeKey<ArboreumAdmin>("ArboreumAdmin")

        override fun install(pipeline: ApplicationCallPipeline, configure: Configuration.() -> Unit): ArboreumAdmin {
            val conf = Configuration().apply(configure)
            if (conf.webjarPath == null) {
                pipeline.install(Webjars) {
                    path = WEBJAR_DEFAULT
                }
            }

            return ArboreumAdmin(conf)
        }
    }
}