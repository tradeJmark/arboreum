package ca.tradejmark.arboreum.db.dao

import ca.tradejmark.arboreum.db.schema.Categories
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable

class CategoryDAO(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<CategoryDAO>(Categories)

    var title: String by Categories.title
    var url: String by Categories.url
    var parent: CategoryDAO? by CategoryDAO optionalReferencedOn Categories.parent
    val children: SizedIterable<CategoryDAO> by CategoryDAO optionalReferrersOn Categories.parent
}