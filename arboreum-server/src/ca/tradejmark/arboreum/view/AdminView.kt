package ca.tradejmark.arboreum.view

import ca.tradejmark.arboreum.db.dao.CategoryDAO
import kotlinx.html.*
import org.jetbrains.exposed.sql.transactions.transaction

object AdminView {
    fun adminHtml(rootCategories: Iterable<CategoryDAO>): HTML.() -> Unit = {
        head {
            title = "Admin - Arboreum"
            link {
                rel = "stylesheet"
                href = "/static/admin.css"
            }
        }
        body {
            dialog {
                id = "addCategoryDialog"
                form {
                    action = "/admin/categories"
                    id = "addCategoryForm"
                    label { +"Title:" }
                    br
                    textInput { name = "title" }
                    br
                    label { +"URL:" }
                    br
                    textInput { name = "url" }
                    br
                    submitInput { value = "Add" }
                }
            }

            div {
                id = "categories"
                h3 { +"Categories" }
                heirarchyList(rootCategories)
                button {
                    id = "newCatButton"
                    +"New Category"
                }
            }
            script {
                src = "/webjars/jquery/jquery.js"
            }
            script {
                src = "/static/scripts/admin.js"
            }
        }
    }

    private fun FlowContent.heirarchyList(roots: Iterable<CategoryDAO>) {
        ul {
            transaction {
                for (category in roots) {
                    li {
                        +"${category.title} (${category.url})"
                        heirarchyList(category.children)
                    }
                }
            }
        }
    }
}