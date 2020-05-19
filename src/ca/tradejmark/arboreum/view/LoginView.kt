package ca.tradejmark.arboreum.view

import kotlinx.html.*

object LoginView {
    fun loginHtml(): HTML.() -> Unit = {
        body {
            form {
                action = "/admin/login"
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

    fun loginResultHtml(result: Boolean, user: String): HTML.() -> Unit = {
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