package ca.tradejmark.arboreum

import io.ktor.application.ApplicationCall
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.ApplicationFeature
import io.ktor.application.call
import io.ktor.response.respondRedirect
import io.ktor.util.AttributeKey

class RedirectOn(conf: Configuration) {
    val condition = conf.condition
    val url = conf.url
    val permanent = conf.permanent

    class Configuration {
        fun condition(cond: ApplicationCall.() -> Boolean) {
            condition = cond
        }
        internal var condition: ApplicationCall.() -> Boolean = { false }
        var url = "/"
        var permanent = false
    }

    private fun intercept(pipeline: ApplicationCallPipeline) = pipeline.intercept(ApplicationCallPipeline.Call) {
        if (condition(call)) {
            call.respondRedirect(url, permanent)
            finish()
        }
    }

    companion object Feature: ApplicationFeature<ApplicationCallPipeline, Configuration, RedirectOn> {
        override val key = AttributeKey<RedirectOn>("RedirectOn")

        override fun install(pipeline: ApplicationCallPipeline, configure: Configuration.() -> Unit): RedirectOn {
            val conf = Configuration().apply(configure)
            val feature = RedirectOn(conf)

            feature.intercept(pipeline)

            return feature
        }
    }
}