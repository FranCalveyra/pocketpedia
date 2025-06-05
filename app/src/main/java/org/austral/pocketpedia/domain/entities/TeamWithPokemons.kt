package org.austral.pocketpedia.domain.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TeamWithPokemons(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "pokemonId",
        associateBy = Junction(PokemonInTeam::class)
    ) val pokemons: List<PokemonEntity>
)
