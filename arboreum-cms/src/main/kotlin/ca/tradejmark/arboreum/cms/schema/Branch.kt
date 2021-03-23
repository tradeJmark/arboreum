package ca.tradejmark.arboreum.cms.schema

data class Branch(val id: String, val name: String,val description: String, val subbranchNames: List<String>) {
    lateinit var leaves: List<Leaf>
}