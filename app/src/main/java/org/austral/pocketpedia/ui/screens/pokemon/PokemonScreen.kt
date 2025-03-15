package org.austral.pocketpedia.ui.screens.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

@Composable
fun PokemonScreen(name: String) {
    val context = LocalContext.current
    val id = remember(name) {
        context.resources.getIdentifier(
            name,
            "drawable",
            context.packageName
        )
    }
    Column {
        Image(
            painter = painterResource(id = id),
            contentDescription = "Screen for Pokemon $name"
        )
    }
}