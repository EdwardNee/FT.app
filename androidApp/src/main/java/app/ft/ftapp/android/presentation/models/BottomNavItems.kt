package app.ft.ftapp.android.presentation.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Data class to keep navigation items info.
 */
data class BottomNavItems(
    val description: String,
    val imageVector: ImageVector? = null,
    val painter: Painter? = null,
    val tabName: String = "",
    val content: (@Composable () -> Unit)? = null
)