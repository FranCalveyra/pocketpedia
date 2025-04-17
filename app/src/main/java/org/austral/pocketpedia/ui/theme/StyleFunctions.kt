package org.austral.pocketpedia.ui.theme

import androidx.compose.ui.graphics.Color
import org.austral.pocketpedia.domain.models.pokemon.Pokemon

fun transformToTitle(name: String): String {
    return name[0].toUpperCase() + name.substring(1)
}

fun getPokemonColor(pokemon: Pokemon?): Color {
    val original = pokemon?.types?.firstOrNull()?.color ?: Color.Red
    return original.copy(
        red = original.red * 0.7f,
        green = original.green * 0.7f,
        blue = original.blue * 0.7f
    )
}

fun clearHyphens(string: String): String {
    return string.split("-").joinToString(" ") { transformToTitle(it) }
}
