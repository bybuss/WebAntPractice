package bob.colbaskin.webantpractice.utils

fun String.extractIdFromIri(): Int {
    return this.substringAfterLast("/").toInt()
}