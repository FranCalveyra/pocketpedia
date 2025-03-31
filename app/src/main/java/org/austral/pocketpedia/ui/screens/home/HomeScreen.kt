package org.austral.pocketpedia.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.austral.pocketpedia.R
import org.austral.pocketpedia.ui.shared.pokemon.card.PokemonCarousel
import org.austral.pocketpedia.ui.shared.text.TypingText

@Composable
fun HomeScreen(navController: NavHostController) {
    val text = LocalContext.current.getString(R.string.welcome_title)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        TypingText(text = text, repeatTyping = true)
        Spacer(modifier = Modifier.height(200.dp))
        PokemonCarousel("Kanto", navController)
        Spacer(modifier = Modifier.height(200.dp))
        PokemonCarousel("Kanto", navController)
    }
}
