package ru.petrolplus.pos.util.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun Context.copyPlainTextToClipboard(label: String, value: String) {
    (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
        ClipData.newPlainText(label, value)
    )
}