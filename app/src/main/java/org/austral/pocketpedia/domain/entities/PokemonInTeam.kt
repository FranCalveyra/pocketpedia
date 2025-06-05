package org.austral.pocketpedia.domain.entities

import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    tableName = "team_pokemon",
    primaryKeys = ["teamId", "pokemonId"],
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["teamId"],
            childColumns = ["teamId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["pokemonId"],
            childColumns = ["pokemonId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PokemonInTeam(
    val teamId: Long,
    val pokemonId: Long
)

