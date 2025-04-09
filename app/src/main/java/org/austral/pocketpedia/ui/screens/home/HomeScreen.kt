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
import org.austral.pocketpedia.domain.models.pokemon.Pokemon
import org.austral.pocketpedia.ui.shared.pokemon.card.PokemonCarousel
import org.austral.pocketpedia.ui.shared.text.TypingText

@Composable
fun HomeScreen(navController: NavHostController) {
    val text = LocalContext.current.getString(R.string.welcome_title)
    val spacing = 120.dp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        TypingText(text = text, repeatTyping = true)
        Spacer(modifier = Modifier.height(spacing))
        PokemonCarousel("Kanto", arrayOfNulls<Pokemon>(10).toList(), navController)
        Spacer(modifier = Modifier.height(spacing))
        PokemonCarousel("Johto", arrayOfNulls<Pokemon>(10).toList(), navController)
        Spacer(modifier = Modifier.height(spacing))
        PokemonCarousel("Hoenn", arrayOfNulls<Pokemon>(10).toList(), navController)
    }
}
