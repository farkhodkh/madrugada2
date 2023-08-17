package ru.petroplus.pos.debug

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap

data class DebitDebugGroup(
    val transaction: SnapshotStateList<Pair<String, Any>> = mutableStateListOf(),
    val transactionsOutput: List<String> = emptyList(),
    val guidParams: SnapshotStateList<Pair<String, Any>> = mutableStateListOf(),
    val guidParamsOutput: List<String> = emptyList(),
)