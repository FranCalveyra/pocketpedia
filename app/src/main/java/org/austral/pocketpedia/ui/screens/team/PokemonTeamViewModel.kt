package org.austral.pocketpedia.ui.screens.team

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.austral.pocketpedia.api.ApiServiceImpl
import org.austral.pocketpedia.domain.entities.PokemonEntity
import org.austral.pocketpedia.domain.entities.PokemonInTeam
import org.austral.pocketpedia.domain.entities.TeamEntity
import org.austral.pocketpedia.domain.mappers.PokemonMapper
import org.austral.pocketpedia.domain.mappers.PokemonTeamMapper
import org.austral.pocketpedia.domain.models.team.PokemonTeam
import org.austral.pocketpedia.infrastructure.storage.local.PocketPediaDatabase
import javax.inject.Inject

@HiltViewModel
class PokemonTeamViewModel @Inject constructor(
    private val apiService: ApiServiceImpl,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    // Observe database for teams and their pokemons
    private val _teams = MutableStateFlow<List<PokemonTeam>>(emptyList())
    val teams = _teams.asStateFlow()
    private val database = PocketPediaDatabase.getDatabase(context)
    private val teamDao = database.teamDao()

    init {
        loadTeams()
    }


    fun createTeam(teamName: String) {
        viewModelScope.launch {
            teamDao.insertTeam(TeamEntity(name = teamName))
        }
    }

    fun addPokemonToTeam(teamName: String, pokemonName: String) {
        apiService.getPokemon(name = pokemonName, context = context, onSuccess = { resp ->
            val pokemon = PokemonMapper.pokemonFromResponse(resp)
            viewModelScope.launch {

                teamDao.insertPokemon(
                    PokemonEntity(
                        pokemonId = pokemon.id,
                        name = pokemon.name,
                        spriteUrl = pokemon.sprites.frontDefault,
                        typesCsv = pokemon.types.joinToString(",") { it.name })
                )

                val team = teamDao.getTeamByName(teamName) ?: return@launch

                teamDao.insertPokemonToTeam(PokemonInTeam(team.teamId, pokemon.id))
            }
        }, onFail = {
            Toast.makeText(context, "Failed to load $pokemonName", Toast.LENGTH_SHORT).show()
        }, loadingFinished = { })
    }

    fun removePokemonFromTeam(teamName: String, pokemonName: String) {
        viewModelScope.launch {
            val team = teamDao.getTeamByName(teamName) ?: return@launch
            // Assuming pokemonName is unique; get pokemon entity
            val pokemons = teamDao.getTeamsWithPokemons().firstOrNull()
                ?.find { it.team.teamId == team.teamId }?.pokemons ?: return@launch
            val pokemonEntity = pokemons.find { it.name == pokemonName } ?: return@launch
            teamDao.removePokemonFromTeam(team.teamId, pokemonEntity.pokemonId)
        }
    }

    private fun loadTeams() {
        viewModelScope.launch {
            teamDao.getTeamsWithPokemons().map { teams ->
                teams.map { twp -> PokemonTeamMapper.fromEntity(twp) }
            }.collect { _teams.value = it }
        }
    }

}