package org.austral.pocketpedia.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val PrimaryLight = Color(0xFFC50D0D)
val SecondaryLight = Color(0xFFF7D02C)
val BackgroundLight = Color(0xFFF5F5F5)
val OnSurfaceLight = Color(0xFF212121)
val ErrorLight = Color(0xFFB00020)

val LightTextColor = Color.Black.copy(alpha = 0.6f)

val ClassicColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = Color.White,
    secondary = SecondaryLight,
    background = BackgroundLight,
    surface = Color.White,
    onSurface = OnSurfaceLight,
    error = ErrorLight,
)

val PrimaryDark = Color(0xFFBB86FC)
val OnPrimaryDark = Color(0xFF121212)
val SecondaryDark = Color(0xFF03DAC6)
val BackgroundDark = Color(0xFF121212)
val SurfaceDark = Color(0xFF1E1E1E)
val OnSurfaceDark = Color(0xFFE1E1E1)
val ErrorDark = Color(0xFFCF6679)


val DarkTextColor = Color.White.copy(alpha = 0.6f)
val PocketPediaDarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    secondary = SecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    error = ErrorDark
)
