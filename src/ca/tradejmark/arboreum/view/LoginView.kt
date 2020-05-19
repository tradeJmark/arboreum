package ca.tradejmark.arboreum.view

import kotlinx.html.*
import javax.swing.text.html.HTMLDocument

object LoginView {
    fun loginHtml(back: String? = null): HTML.() -> Unit = {
        body {
            form {
                if (back != null) action = "?back=$back"
                method = FormMethod.post
                label {
                    +"Username:"
                }
                br
                textInput {
                    name = "user"
                }
                br
                label {
                    +"Password:"
                }
                br
                passwordInput {
                    name = "pass"
                }
                br
                submitInput {
                    value = "Login"
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