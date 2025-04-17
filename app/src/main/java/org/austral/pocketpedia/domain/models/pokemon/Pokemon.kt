package org.austral.pocketpedia.domain.models.pokemon

import org.austral.pocketpedia.domain.models.response.Ability
import org.austral.pocketpedia.domain.models.response.Sprite
import org.austral.pocketpedia.domain.models.response.Stat

data class Pokemon(
    val id: Long,
    val name: String,
    val height: Long,
    val weight: Long,
    val abilities: List<Ability>,
    val types: List<PokemonType>,
    val stats: List<Stat>,
    val sprites: Sprite
)