package org.austral.pocketpedia.ui.screens.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext

import org.austral.pocketpedia.R
import org.austral.pocketpedia.ui.shared.navigation.text.TypingText

@Composable
fun HomeScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val text = LocalContext.current.getString(R.string.welcome_title)
        TypingText(text = text, repeatTyping = true)
        // Carousels
    }
}