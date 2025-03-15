package org.austral.pocketpedia.ui.screens.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import org.austral.pocketpedia.ui.shared.navigation.BottomNavbar
import org.austral.pocketpedia.ui.shared.navigation.text.TypingText

@Composable
fun HomeScreen() {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        TypingText(text = "Welcome to PocketPedia!", repeatTyping = true)
        BottomNavbar()
    }
}