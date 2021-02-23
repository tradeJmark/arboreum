package ca.tradejmark.arboreum.db.schema

import org.jetbrains.exposed.dao.id.IntIdTable

object Categories: IntIdTable() {
    val title = varchar("title", 20)
    val url = varchar("url", 20)
    val parent = reference("parent", Categories).nullable()
}