package bob.colbaskin.webantpractice.common.utils

fun String.extractIdFromIri(): Int {
    return this.substringAfterLast("/").toInt()
}