package org.austral.pocketpedia.ui.screens.pokedex

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.austral.pocketpedia.api.ApiServiceImpl
import org.austral.pocketpedia.domain.mappers.PokemonMapper
import org.austral.pocketpedia.domain.models.pokemon.Pokemon
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val apiService: ApiServiceImpl,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _searchResults = MutableStateFlow<List<Pokemon>>(emptyList())
    val searchResults: StateFlow<List<Pokemon>> = _searchResults

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    // paginate through name batches until we find a batch whose first name > query prefix
    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
        doSearch(newQuery.trim().lowercase())
    }

    /** also invoked when the user taps “search” on the keyboard */
    fun onSearch() {
        doSearch(_query.value.trim().lowercase())
    }

    private fun doSearch(prefix: String) {
        if (prefix.isEmpty()) {
            _searchResults.value = emptyList()
            return
        }

        _loading.value = true
        _searchResults.value = emptyList()

        // Start at offset 0
        searchBatch(prefix, limit = 100, offset = 0)
    }

    private fun searchBatch(prefix: String, limit: Int, offset: Int) {
        apiService.getPokemonNamesBatch(
            limit = limit,
            offset = offset,
            context = context,
            onSuccess = { names ->
                // filter only those that startWith the prefix
                val matched = names
                    .filter { it.startsWith(prefix, ignoreCase = true) }
                    .take(10) // preview up to 10
                if (matched.isNotEmpty()) {
                    fetchAllMatched(prefix, matched)
                } else if (names.size == limit) {
                    // not found yet, fetch next page
                    searchBatch(prefix, limit, offset + limit)
                } else {
                    // no more pages
                    _loading.value = false
                }
            },
            onFail = {
                _loading.value = false
                _searchResults.value = emptyList()
            },
            loadingFinished = { /* no-op; we manage loading manually */ }
        )
    }

    private fun fetchAllMatched(prefix: String, matchedNames: List<String>) {
        val results = mutableListOf<Pokemon>()
        var pending = matchedNames.size

        matchedNames.forEach { name ->
            apiService.getPokemon(
                name = name,
                context = context,
                onSuccess = { resp ->
                    results.add(PokemonMapper.pokemonFromResponse(resp))
                    if (--pending == 0) {
                        _searchResults.value = results.sortedBy { it.id }
                        _loading.value = false
                    }
                },
                onFail = {
                    if (--pending == 0) {
                        _searchResults.value = results.sortedBy { it.id }
                        _loading.value = false
                    }
                },
                loadingFinished = { /*no-op*/ }
            )
        }
    }
}



