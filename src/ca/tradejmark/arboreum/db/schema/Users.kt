package ca.tradejmark.arboreum.db.schema

import org.jetbrains.exposed.dao.id.IntIdTable

object Users: IntIdTable() {
    val username = varchar("username", 20).uniqueIndex()
    val password = binary("password",32)
    val salt = binary("salt", 16)
    val iterations = integer("iterations")
}