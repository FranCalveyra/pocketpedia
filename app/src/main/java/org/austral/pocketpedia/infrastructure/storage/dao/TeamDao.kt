package org.austral.pocketpedia.infrastructure.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.austral.pocketpedia.domain.entities.PokemonEntity
import org.austral.pocketpedia.domain.entities.PokemonInTeam
import org.austral.pocketpedia.domain.entities.TeamEntity
import org.austral.pocketpedia.domain.entities.TeamWithPokemons

@Dao
interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertTeam(team: TeamEntity): Long

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertPokemon(pokemon: PokemonEntity): Long

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertPokemonToTeam(crossRef: PokemonInTeam)

    @Transaction
    @Query("SELECT * FROM teams")
    fun getTeamsWithPokemons(): Flow<List<TeamWithPokemons>>

    @Query("SELECT * FROM teams WHERE name = :name LIMIT 1")
    suspend fun getTeamByName(name: String): TeamEntity?

    @Query("DELETE FROM team_pokemon WHERE teamId = :teamId AND pokemonId = :pokemonId")
    suspend fun removePokemonFromTeam(teamId: Long, pokemonId: Long)

    @Query("DELETE FROM teams WHERE teamId = :teamId")
    suspend fun deleteTeam(teamId: Long)
}