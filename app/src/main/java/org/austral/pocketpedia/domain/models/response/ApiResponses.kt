package org.austral.pocketpedia.domain.models.response


data class RegionResponse(
    val pokedexes: List<NamedAPIResource>
)

data class PokedexResponse(
    val pokemonEntries: List<PokemonEntry>
)

data class NamedAPIResource(
    val name: String,
    val url: String
)

data class PokemonEntry(
    val entryNumber: Int,
    val pokemonSpecies: NamedAPIResource
)

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<NamedAPIResource>
)