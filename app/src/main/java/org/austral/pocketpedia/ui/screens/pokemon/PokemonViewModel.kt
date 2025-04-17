package org.austral.pocketpedia.ui.screens.pokemon

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.austral.pocketpedia.api.ApiServiceImpl
import org.austral.pocketpedia.domain.mappers.PokemonMapper
import org.austral.pocketpedia.domain.models.pokemon.Pokemon
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiServiceImpl,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var _pokemon = MutableStateFlow<Pokemon?>(null)
    val pokemon = _pokemon.asStateFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _retry = MutableStateFlow(false)
    val retry = _retry.asStateFlow()

    init {
        val name = savedStateHandle.get<String>("pokemonName")
        if (name != null) {
            fetchPokemon(name)
        } else {
            _retry.value = true
        }
    }


    fun fetchPokemon(name: String) {
        viewModelScope.launch {
            _isLoading.emit(true)
            apiService.getPokemon(
                name = name,
                context = context,
                onSuccess = {
                    viewModelScope.launch { _pokemon.emit(PokemonMapper.pokemonFromResponse(it)) }
                },
                onFail = {
                    viewModelScope.launch { _pokemon.emit(null) }
                },
                loadingFinished = {
                    viewModelScope.launch { _isLoading.value = false }
                }
            )
        }
    }

    fun retryApiCall(name: String) {
        fetchPokemon(name)
    }

}
