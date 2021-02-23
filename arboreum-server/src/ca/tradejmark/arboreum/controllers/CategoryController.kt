package ca.tradejmark.arboreum.controllers

import ca.tradejmark.arboreum.db.dao.CategoryDAO
import ca.tradejmark.arboreum.model.Category
import org.jetbrains.exposed.sql.transactions.transaction

object CategoryController {
    fun addCategory(category: Category) = transaction {
        CategoryDAO.new {
            title = category.title
            url = category.url
        }
    }
}