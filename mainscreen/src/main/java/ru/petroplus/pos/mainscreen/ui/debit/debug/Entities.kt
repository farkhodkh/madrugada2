package ru.petroplus.pos.mainscreen.ui.debit.debug

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class DebitDebugGroup(
    val transaction: SnapshotStateList<Pair<String, Any>> = mutableStateListOf(),
    val transactionsOutput: List<String> = emptyList(),
    val guidParams: SnapshotStateList<Pair<String, Any>> = mutableStateListOf(),
    val guidParamsOutput: List<String> = emptyList(),
)