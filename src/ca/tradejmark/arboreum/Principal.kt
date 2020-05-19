package ca.tradejmark.arboreum

import ca.tradejmark.arboreum.model.User
import io.ktor.auth.Principal

data class Principal(val user: User): Principal