package bob.colbaskin.webantpractice.design_system.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun String.toDate(): Date {
    return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(this) ?: Date()
}