package ca.tradejmark.arboreum.view

import kotlinx.html.*
import javax.swing.text.html.HTMLDocument

object LoginView {
    fun loginHtml(back: String? = null): HTML.() -> Unit = {
        head {
            title = "Login"
            link {
                rel = "stylesheet"
                href = "/static/login.css"
            }
        }
        body {
            div {
                classes = setOf("content")
                form {
                    if (back != null) action = "?back=$back"
                    method = FormMethod.post
                    div {
                        label {
                            +"Username:"
                        }
                        br
                        textInput {
                            classes = setOf("textbox")
                            name = "user"
                        }
                    }
                    br
                    div {
                        label {
                            +"Password:"
                        }
                        br
                        passwordInput {
                            classes = setOf("textbox")
                            name = "pass"
                        }
                    }
                    br
                    submitInput {
                        value = "Login"
                    }
                }
            }
        }
    }

    fun loginResultHtml(result: Boolean, user: String?): HTML.() -> Unit = {
        body {
            if (result) {
                p {
                    +"Welcome, $user."
                }
            }
            else {
                p {
                    +"Login failed. "
                    a {
                        href = "/admin/login"
                        +"Retry."
                    }
                }
            }
        }
    }
}