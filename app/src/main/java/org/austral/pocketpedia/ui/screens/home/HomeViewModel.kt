package org.austral.pocketpedia.ui.screens.home

import android.content.Context
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
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiServiceImpl
) : ViewModel() {

    private val _pokemonLists = MutableStateFlow<List<List<Pokemon>>>(emptyList())
    val pokemonLists = _pokemonLists.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    val regions = listOf(
        "Kanto", "Johto", "Hoenn", "Sinnoh", "Unova", "Kalos", "Alola", "Galar", "Paldea"
    )

    init {
        _pokemonLists.value = List(regions.size) { emptyList() }

        regions.forEachIndexed { index, region ->
            fetchPokemonListByRegion(region.lowercase(), 10, index)
        }
    }

    fun fetchPokemonListByRegion(region: String, limit: Int, index: Int) {
        _isLoading.value = true


        apiService.getPokemonListByRegion(
            region = region,
            limit = limit,
            context = context,
            onSuccess = { result ->
                val mappedAndSorted = result
                    .map { PokemonMapper.pokemonFromResponse(it) }
                    .sortedBy { it.id }

                viewModelScope.launch {
                    val current = _pokemonLists.value.toMutableList()
                    current[index] = mappedAndSorted
                    _pokemonLists.emit(current)
                }
            },
            onFail = {},
            loadingFinished = {
                viewModelScope.launch { _isLoading.emit(false) }
            }
        )
    }
}

