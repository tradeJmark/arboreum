package ca.tradejmark.arboreum.cms.schema

data class Tree(val name: String, val branches: List<Tree>)