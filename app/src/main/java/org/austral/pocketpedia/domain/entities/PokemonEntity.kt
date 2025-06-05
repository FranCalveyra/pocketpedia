package org.austral.pocketpedia.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey val pokemonId: Long,
    val name: String,
    val spriteUrl: String,
    val typesCsv: String
)