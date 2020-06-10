package ca.tradejmark.arboreum.model

data class PasswordInfo(val password: ByteArray, val salt: ByteArray, val iterations: Int)