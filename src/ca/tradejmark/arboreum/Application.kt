package ca.tradejmark.arboreum
import ca.tradejmark.arboreum.model.User
import ca.tradejmark.arboreum.view.LoginView
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import kotlinx.css.*
import io.ktor.http.content.*
import io.ktor.sessions.*
import io.ktor.features.*
import io.ktor.util.date.*
import io.ktor.server.engine.*
import io.ktor.auth.*
import io.ktor.gson.*
import io.ktor.request.uri
import io.ktor.util.url

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val gson = Gson()
    install(Sessions) {
        cookie<Principal>("ARB_SESSION", SessionStorageMemory()) {
            cookie.extensions["SameSite"] = "lax"
            serializer = object: SessionSerializer<Principal> {
                override fun deserialize(text: String): Principal = gson.fromJson(text, Principal::class.java)
                override fun serialize(session: Principal): String = gson.toJson(session)
            }
        }
    }

    install(AutoHeadResponse)

    install(ConditionalHeaders)

    install(CachingHeaders) {
        options { outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60), expires = null as? GMTDate?)
                else -> null
            }
        }
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    install(ForwardedHeaderSupport) // WARNING: for security, do not include this if not behind a reverse proxy
    install(XForwardedHeaderSupport) // WARNING: for security, do not include this if not behind a reverse proxy

    install(ShutDownUrl.ApplicationCallFeature) {
        // The URL that will be intercepted (you can also use the application.conf's ktor.deployment.shutdown.url key)
        shutDownUrl = "/ktor/application/shutdown"
        // A function that will be executed to get the exit code of the process
        exitCodeSupplier = { 0 } // ApplicationCall.() -> Int
    }

    install(Authentication) {
        form("login") {
            userParamName = "user"
            passwordParamName = "pass"
            challenge {
                val failures = call.authentication.allFailures
                when {
                    failures.contains(AuthenticationFailedCause.NoCredentials) -> call.respondRedirect("/admin/login?back=${call.request.uri}", false)
                    failures.contains(AuthenticationFailedCause.InvalidCredentials) -> call.respondRedirect("/admin/login?failed=true", false)
                    else -> call.respondRedirect("/admin/login", false)
                }
            }
            validate { Principal(User(0, it.name)) }
        }
        session<Principal>("admin") {
            challenge {
                call.respondRedirect("/admin/login?back=${call.request.uri}", false)
            }
            validate { it }
        }
    }

    install(ContentNegotiation) {
        register(ContentType.Application.Json, GsonConverter(gson))
    }

    /*install(RedirectOn) {
        condition {
            this.request.uri == "/admin"
        }
        url = "/"
    }*/

    routing {
        route("admin") {
            route("login") {
                get {
                    val redir = call.request.queryParameters["back"]
                    call.respondHtml(block = LoginView.loginHtml(redir))
                }
                authenticate("login") {
                    post {
                        val principal = call.principal<Principal>()
                        val redir = call.request.queryParameters["back"]
                        call.sessions.set(principal)
                        if (redir != null) {
                            call.respondRedirect(redir, false)
                        }
                        else call.respondHtml(
                            block = LoginView.loginResultHtml(
                                principal != null,
                                principal?.user?.username
                            )
                        )
                    }
                }
            }
            authenticate("admin") {
                get {
                    call.respondText("test", contentType = ContentType.Text.Plain)
                }
            }
        }

        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/styles.css") {
            call.respondCss {
                body {
                    backgroundColor = Color.red
                }
                p {
                    fontSize = 2.em
                }
                rule("p.myclass") {
                    color = Color.blue
                }
            }
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        install(StatusPages) {
            exception<AuthenticationException> { cause ->
                call.respond(HttpStatusCode.Unauthorized, cause)
            }
            exception<AuthorizationException> { cause ->
                call.respond(HttpStatusCode.Forbidden, cause)
            }

        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

fun FlowOrMetaDataContent.styleCss(builder: CSSBuilder.() -> Unit) {
    style(type = ContentType.Text.CSS.toString()) {
        +CSSBuilder().apply(builder).toString()
    }
}

fun CommonAttributeGroupFacade.style(builder: CSSBuilder.() -> Unit) {
    this.style = CSSBuilder().apply(builder).toString().trim()
}

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
