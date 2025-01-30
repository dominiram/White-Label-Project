package utils

fun String.toColor(): Long = this.removePrefix("#").toLong(16) or 0x00000000FF000000
