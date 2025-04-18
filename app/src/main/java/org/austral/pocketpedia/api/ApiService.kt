package org.austral.pocketpedia.api

import org.austral.pocketpedia.domain.models.response.PokedexResponse
import org.austral.pocketpedia.domain.models.response.PokemonListResponse
import org.austral.pocketpedia.domain.models.response.PokemonResponse
import org.austral.pocketpedia.domain.models.response.RegionResponse
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Path
import retrofit.http.Query

interface ApiService {

    @GET("pokemon/{name}")
    fun getPokemon(@Path("name") name: String): Call<PokemonResponse>

    @GET("region/{region}")
    fun getRegion(@Path("region") region: String): Call<RegionResponse>

    @GET("pokedex/{pokedexName}")
    fun getPokedex(@Path("pokedexName") name: String): Call<PokedexResponse>

    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<PokemonListResponse>

}
