package org.austral.pocketpedia.api

import android.content.Context
import android.widget.Toast
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.models.response.PokedexResponse
import org.austral.pocketpedia.domain.models.response.PokemonListResponse
import org.austral.pocketpedia.domain.models.response.PokemonResponse
import org.austral.pocketpedia.domain.models.response.RegionResponse
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject

class ApiServiceImpl @Inject constructor() {

    fun getPokemon(
        name: String,
        context: Context,
        onSuccess: (PokemonResponse) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val service = buildService(context)
        service.getPokemon(name).enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(response: Response<PokemonResponse>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response?.isSuccess == true) {
                    onSuccess(response.body())
                } else {
                    showToast(context, R.string.pokemon_not_found)
                    onFail()
                }
            }

            override fun onFailure(t: Throwable?) {
                showToast(context, R.string.pokemon_not_found)
                onFail()
                loadingFinished()
            }
        })
    }

    fun getPokemonListByRegion(
        region: String,
        limit: Int,
        context: Context,
        onSuccess: (List<PokemonResponse>) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val service = buildService(context)

        service.getRegion(region).enqueue(object : Callback<RegionResponse> {
            override fun onResponse(response: Response<RegionResponse>?, retrofit: Retrofit?) {
                if (response?.isSuccess != true) return failAndFinish(
                    context,
                    onFail,
                    loadingFinished
                )

                val pokedexName = response.body()
                    .pokedexes
                    .firstOrNull { it.name.contains(region, ignoreCase = true) }
                    ?.name
                    ?: response.body().pokedexes.firstOrNull()?.name

                if (pokedexName == null) return failAndFinish(context, onFail, loadingFinished)

                service.getPokedex(pokedexName).enqueue(object : Callback<PokedexResponse> {
                    override fun onResponse(
                        response: Response<PokedexResponse>?,
                        retrofit: Retrofit?
                    ) {
                        val entries = response?.body()?.pokemonEntries?.take(limit)
                        if (entries == null) return failAndFinish(context, onFail, loadingFinished)

                        fetchPokemonList(
                            service,
                            entries.map { it.pokemonSpecies.name }.toList(),
                            onSuccess,
                            loadingFinished
                        )
                    }

                    override fun onFailure(t: Throwable?) {
                        failAndFinish(context, onFail, loadingFinished)
                    }
                })
            }

            override fun onFailure(t: Throwable?) {
                failAndFinish(context, onFail, loadingFinished)
            }
        })
    }

    fun getPokemonNamesBatch(
        limit: Int,
        offset: Int,
        context: Context,
        onSuccess: (List<String>) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val service = buildService(context)
        service.getPokemonList(limit, offset).enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(
                response: Response<PokemonListResponse>?,
                retrofit: Retrofit?
            ) {
                if (response?.isSuccess == true) {
                    val names = response.body().results.map { it.name }
                    onSuccess(names)
                } else {
                    showToast(context, R.string.pokemon_not_found)
                    onFail()
                }
                loadingFinished()
            }

            override fun onFailure(t: Throwable?) {
                showToast(context, R.string.pokemon_not_found)
                onFail()
                loadingFinished()
            }
        })
    }


    private fun fetchPokemonList(
        service: ApiService,
        names: List<String>,
        onSuccess: (List<PokemonResponse>) -> Unit,
        loadingFinished: () -> Unit
    ) {
        val pokemonList = mutableListOf<PokemonResponse>()
        var pending = names.size

        names.forEach { name ->
            service.getPokemon(name).enqueue(object : Callback<PokemonResponse> {
                override fun onResponse(response: Response<PokemonResponse>?, retrofit: Retrofit?) {
                    response?.body()?.let { pokemonList.add(it) }
                    checkPending()
                }

                override fun onFailure(t: Throwable?) {
                    checkPending()
                }

                private fun checkPending() {
                    pending--
                    if (pending == 0) {
                        loadingFinished()
                        onSuccess(pokemonList)
                    }
                }
            })
        }
    }

    private fun buildService(context: Context): ApiService {
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.pokeapi_url))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(ApiService::class.java)
    }

    private fun showToast(context: Context, messageResId: Int) {
        Toast.makeText(context, context.getString(messageResId), Toast.LENGTH_SHORT).show()
    }

    private fun failAndFinish(context: Context, onFail: () -> Unit, loadingFinished: () -> Unit) {
        showToast(context, R.string.region_not_found)
        onFail()
        loadingFinished()
    }
}
