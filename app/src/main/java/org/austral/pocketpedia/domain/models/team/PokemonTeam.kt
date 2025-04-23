package org.austral.pocketpedia.domain.models.team

import org.austral.pocketpedia.domain.models.pokemon.Pokemon

data class PokemonTeam(
    val teamName: String,
    val team: List<Pokemon>
) // Should not have more than 6 Pokemon per team
