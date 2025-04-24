package org.austral.pocketpedia.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val primaryColors = listOf<Color>(
    Color(0xFFFFC8CC),
    Color(0xFFFEACB3),
    Color(0xFFFF909A),
    Color(0xFFFE6E7A),
    Color(0xFFF35B68),
    Color(0xFFD94652),
    Color(0xFFAD3A44),
    Color(0xFF95333C),
    Color(0xFF812D34),
)

val BackgroundGradient = Brush.verticalGradient(
    listOf<Color>(
        primaryColors[3],
        primaryColors[5],
        primaryColors.last()
    )
)


private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = primaryColors[4],
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun PocketPediaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> PocketPediaDarkColorScheme
        else -> ClassicColorScheme
    }

    val typography = typography(darkTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}

@Composable
private fun typography(darkTheme: Boolean = isSystemInDarkTheme()): androidx.compose.material3.Typography {
    if (darkTheme) {
        return Typography.copy(
            titleLarge = Typography.titleLarge.copy(color = DarkTextColor),
            titleMedium = Typography.titleMedium.copy(color = DarkTextColor),
            titleSmall = Typography.titleSmall.copy(color = DarkTextColor),
            bodyLarge = Typography.bodyLarge.copy(color = DarkTextColor),
            bodyMedium = Typography.bodyMedium.copy(color = DarkTextColor),
            bodySmall = Typography.bodySmall.copy(color = DarkTextColor),
        )
    } else return Typography
}