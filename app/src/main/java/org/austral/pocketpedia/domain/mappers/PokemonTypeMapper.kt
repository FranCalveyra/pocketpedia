package org.austral.pocketpedia.domain.mappers

import org.austral.pocketpedia.domain.models.pokemon.PokemonType

class PokemonTypeMapper {
    companion object {
        fun fromString(pokemonType: String): PokemonType {
            return when (pokemonType) {
                "normal" -> PokemonType.NORMAL
                "fire" -> PokemonType.FIRE
                "water" -> PokemonType.WATER
                "electric" -> PokemonType.ELECTRIC
                "grass" -> PokemonType.GRASS
                "ice" -> PokemonType.ICE
                "fighting" -> PokemonType.FIGHTING
                "poison" -> PokemonType.POISON
                "ground" -> PokemonType.GROUND
                "flying" -> PokemonType.FLYING
                "psychic" -> PokemonType.PSYCHIC
                "bug" -> PokemonType.BUG
                "rock" -> PokemonType.ROCK
                "ghost" -> PokemonType.GHOST
                "dragon" -> PokemonType.DRAGON
                "dark" -> PokemonType.DARK
                "steel" -> PokemonType.STEEL
                "fairy" -> PokemonType.FAIRY
                else -> PokemonType.UNKNOWN
            }
        }
    }
}