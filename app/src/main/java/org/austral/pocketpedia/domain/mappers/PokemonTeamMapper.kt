package org.austral.pocketpedia.domain.mappers

import org.austral.pocketpedia.domain.entities.TeamWithPokemons
import org.austral.pocketpedia.domain.models.team.PokemonTeam

class PokemonTeamMapper {
    companion object {
        fun fromEntity(twp: TeamWithPokemons): PokemonTeam {
            return PokemonTeam(
                teamName = twp.team.name,
                team = twp.pokemons.map { pe ->
                    PokemonMapper.pokemonFromEntity(pe)
                }
            )
        }
    }
}