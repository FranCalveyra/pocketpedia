package org.austral.pocketpedia.ui.screens.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.austral.pocketpedia.domain.models.pokemon.Pokemon
import org.austral.pocketpedia.domain.models.team.PokemonTeam
import javax.inject.Inject

@HiltViewModel
class PokemonTeamViewModel @Inject constructor() : ViewModel() {
    // TODO: get teams from datasource
    private var _teams = MutableStateFlow(listOf<PokemonTeam>())
    val teams = _teams.asStateFlow()

    fun createTeam(teamName: String) {
        viewModelScope.launch {
            _teams.emit(
                _teams.value + PokemonTeam(teamName, listOf())
            )
        }
    }

    fun addPokemonToTeam(teamName: String, newPokemon: Pokemon) {
        viewModelScope.launch {
            val teamToChange = _teams.value.find { team -> team.teamName == teamName }
            if (teamToChange == null) return@launch

            val newTeam = teamToChange.copy(
                team = teamToChange.team + newPokemon
            )
            _teams.emit(
                _teams.value - teamToChange + newTeam
            )
        }
    }

    fun removePokemonFromTeam(teamName: String, pokemonName: String) { // Easier to handle a name?
        viewModelScope.launch {
            val teamToChange = _teams.value.find { team -> team.teamName == teamName }
            if (teamToChange == null) return@launch

            val pokemonToRemove = teamToChange.team.find { pokemon -> pokemon.name == pokemonName }

            if (pokemonToRemove == null) return@launch

            val updatedTeam = teamToChange.copy(
                team = teamToChange.team - pokemonToRemove
            )
            _teams.emit(
                _teams.value - teamToChange + updatedTeam
            )
        }
    }
}