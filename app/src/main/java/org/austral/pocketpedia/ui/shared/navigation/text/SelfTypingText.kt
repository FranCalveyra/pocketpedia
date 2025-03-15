package org.austral.pocketpedia.ui.shared.navigation.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.austral.pocketpedia.R

@Composable
fun TypingText(
    text: String,
    typingSpeed: Long = 100L,
    repeatTyping: Boolean = false // Toggle infinite writing effect
) {
    var displayedText by remember { mutableStateOf("") }
    var isDeleting by remember { mutableStateOf(false) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var showCursor by remember { mutableStateOf(true) }

    LaunchedEffect(text, repeatTyping) {
        while (true) {
            if (!isDeleting) {
                if (currentIndex < text.length) {
                    displayedText = text.substring(0, currentIndex + 1)
                    currentIndex++
                    delay(typingSpeed)
                } else if (repeatTyping) {
                    delay(1000L) // Pause before erasing
                    isDeleting = true
                }
            } else {
                if (currentIndex > 0) {
                    displayedText = text.substring(0, currentIndex - 1)
                    currentIndex--
                    delay(typingSpeed / 2)
                } else {
                    isDeleting = false
                }
            }
            if (!repeatTyping) break
        }
    }

    // Blinking Cursor Effect
    LaunchedEffect(Unit) {
        while (true) {
            showCursor = !showCursor
            delay(500L)
        }
    }

    Text(
        text = displayedText + if (showCursor) "▌" else "",
        fontSize = 24.sp,
        fontFamily = FontFamily(Font(R.font.jetbrains_mono_regular)),
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(8.dp)
    )
}

