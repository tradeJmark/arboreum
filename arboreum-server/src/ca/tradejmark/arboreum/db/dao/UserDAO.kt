package ca.tradejmark.arboreum.db.dao

import ca.tradejmark.arboreum.db.schema.Users
import ca.tradejmark.arboreum.model.PasswordInfo
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserDAO(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<UserDAO>(Users) {
        fun fromUsername(username: String): UserDAO? = find { Users.username eq username }.firstOrNull()
    }
    var username: String by Users.username
    var password: ByteArray by Users.password
    var salt: ByteArray by Users.salt
    var iterations: Int by Users.iterations
    fun passwordInfo() = PasswordInfo(password, salt, iterations)
}