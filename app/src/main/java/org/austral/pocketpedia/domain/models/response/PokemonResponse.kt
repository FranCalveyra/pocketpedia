package org.austral.pocketpedia.domain.models.response

data class PokemonResponse(
    val id: Long,
    val name: String,
    val height: Long,
    val weight: Long,
    val abilities: List<Ability>,
    val types: List<Type>,
    val stats: List<Stat>,
    val sprites: Sprite
)


data class Ability(
    val isHidden: Boolean,
    val slot: Long,
    val ability: NamedAPIResource
)

data class Type(
    val slot: Long,
    val type: NamedAPIResource
)

data class Stat(
    val baseStat: Long,
    val effort: Long,
    val stat: NamedAPIResource
)

data class Sprite(
    val frontDefault: String
)