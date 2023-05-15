package ru.petroplus.pos.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
)