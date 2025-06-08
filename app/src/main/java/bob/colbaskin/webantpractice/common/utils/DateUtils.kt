package bob.colbaskin.webantpractice.common.utils

import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

fun String.parseToMillis(): Long {
    return OffsetDateTime.parse(this, formatter).toInstant().toEpochMilli()
}

fun Long.formatFromMillis(): String {
    return OffsetDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneOffset.UTC).format(formatter)
}