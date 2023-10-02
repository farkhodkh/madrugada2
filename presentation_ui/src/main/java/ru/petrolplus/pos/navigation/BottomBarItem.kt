package ru.petrolplus.pos.navigation

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Элемент для отображения единицы BottomBar
 */
data class BottomBarItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
)