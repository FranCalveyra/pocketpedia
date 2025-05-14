package org.austral.pocketpedia.ui.screens.team

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.austral.pocketpedia.R
import org.austral.pocketpedia.api.ApiServiceImpl
import org.austral.pocketpedia.domain.mappers.PokemonMapper
import org.austral.pocketpedia.domain.models.pokemon.Pokemon
import org.austral.pocketpedia.domain.models.team.PokemonTeam
import org.austral.pocketpedia.security.biometric.BiometricAuthManager
import javax.inject.Inject

@HiltViewModel
class PokemonTeamViewModel @Inject constructor(
    private val apiService: ApiServiceImpl,
    @ApplicationContext private val context: Context,
    private val biometricAuthManager: BiometricAuthManager,
) : ViewModel() {
    private var _teams = MutableStateFlow(listOf<PokemonTeam>())
    val teams = _teams.asStateFlow()

    private var _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    fun authenticate(context: Context) {
        biometricAuthManager.authenticate(
            context,
            onError = {
                _isAuthenticated.value = false
                showShortToast(context.getString(R.string.authentication_error))
            },
            onSuccess = {
                _isAuthenticated.value = true
            },
            onFail = {
                _isAuthenticated.value = false
                showShortToast(context.getString(R.string.authentication_failed))
            }
        )
    }

    private fun showShortToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun createTeam(teamName: String) {
        viewModelScope.launch {
            _teams.emit(
                _teams.value + PokemonTeam(teamName, listOf())
            )
        }
    }


    fun addPokemonToTeam(teamName: String, pokemonName: String) {
        apiService.getPokemon(
            name = pokemonName,
            context = context,
            onSuccess = { resp ->
                val pokemon = PokemonMapper.pokemonFromResponse(resp)
                addPokemon(teamName, pokemon)
            },
            onFail = {
                Toast.makeText(context, "Failed to load $pokemonName", Toast.LENGTH_SHORT).show()
            },
            loadingFinished = { }
        )
    }


    private fun addPokemon(teamName: String, newPokemon: Pokemon) = viewModelScope.launch {
        val team = _teams.value.find { it.teamName == teamName } ?: return@launch
        val updated = team.copy(team = team.team + newPokemon)
        _teams.emit(_teams.value - team + updated)
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

    fun loadTeams() {} // TODO: integrate with local database
}