package ru.petrolplus.pos.util.ext

fun String.isNotConfigurationCommentedLine() =
    this.startsWith(";") || this.startsWith("[")
