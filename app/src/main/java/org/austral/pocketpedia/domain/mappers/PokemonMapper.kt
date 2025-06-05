package org.austral.pocketpedia.domain.mappers

import org.austral.pocketpedia.domain.entities.PokemonEntity
import org.austral.pocketpedia.domain.models.pokemon.Pokemon
import org.austral.pocketpedia.domain.models.pokemon.PokemonType
import org.austral.pocketpedia.domain.models.response.PokemonResponse
import org.austral.pocketpedia.domain.models.response.Sprite

class PokemonMapper {
    companion object {
        fun pokemonFromResponse(response: PokemonResponse): Pokemon {
            return Pokemon(
                id = response.id,
                name = response.name,
                height = response.height,
                weight = response.weight,
                abilities = response.abilities,
                sprites = response.sprites,
                stats = response.stats,
                types = response.types.map { mapStringToPokemonType(it.type.name) }
            )
        }

        fun pokemonFromEntity(pokemonEntity: PokemonEntity): Pokemon {
            return Pokemon(
                id = pokemonEntity.pokemonId,
                name = pokemonEntity.name,
                height = 0L,
                weight = 0L,
                abilities = emptyList(),
                types = pokemonEntity.typesCsv.split(",")
                    .map { mapStringToPokemonType(it) },
                stats = emptyList(),
                sprites = Sprite(frontDefault = pokemonEntity.spriteUrl)
            )
        }

        fun mapStringToPokemonType(name: String): PokemonType {
            return PokemonType.entries.find {
                it.name.equals(name, ignoreCase = true)
            } ?: PokemonType.UNKNOWN
        }

    }
}