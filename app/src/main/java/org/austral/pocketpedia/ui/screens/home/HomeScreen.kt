package org.austral.pocketpedia.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import org.austral.pocketpedia.R
import org.austral.pocketpedia.ui.shared.pokemon.card.PokemonCarousel
import org.austral.pocketpedia.ui.shared.text.TypingText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val text = LocalContext.current.getString(R.string.welcome_title)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TypingText(text = text, repeatTyping = true)
        PokemonCarousel()
    }
}
