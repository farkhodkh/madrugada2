package ru.petroplus.pos.util.ext

fun String.isNotConfigurationCommentedLine() =
    this.startsWith(";") || this.startsWith("[")
