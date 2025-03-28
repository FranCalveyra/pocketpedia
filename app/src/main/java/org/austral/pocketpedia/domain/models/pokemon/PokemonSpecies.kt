package org.austral.pocketpedia.domain.models.pokemon

data class PokemonSpecies (
    val id: Long,
    val name: String,
    val order: Long,
    val genderRate: Long,
    val captureRate: Long,
    val baseHappiness: Long,
    val isBaby: Boolean,
    val isLegendary: Boolean,
    val isMythical: Boolean,
    val hatchCounter: Long,
    val hasGenderDifferences: Boolean,
    val formsSwitchable: Boolean,
    val growthRate: TupleEntry,
    val pokedexNumbers: List<PokedexNumber>,
    val eggGroups: List<TupleEntry>,
    val color: TupleEntry,
    val shape: TupleEntry,
    val evolvesFromSpecies: TupleEntry,
    val evolutionChain: EvolutionChain,
    val habitat: Any? = null,
    val generation: TupleEntry,
    val names: List<Name>,
    val flavorTextEntries: List<FlavorTextEntry>,
    val formDescriptions: List<FormDescription>,
    val genera: List<Genus>,
    val varieties: List<Variety>
)

data class TupleEntry (
    val name: String,
    val url: String
)

data class EvolutionChain (
    val url: String
)

data class FlavorTextEntry (
    val flavorText: String,
    val language: TupleEntry,
    val version: TupleEntry
)

data class FormDescription (
    val description: String,
    val language: TupleEntry
)

data class Genus (
    val genus: String,
    val language: TupleEntry
)

data class Name (
    val name: String,
    val language: TupleEntry
)

data class PokedexNumber (
    val entryNumber: Long,
    val pokedex: TupleEntry
)

data class Variety (
    val isDefault: Boolean,
    val pokemon: TupleEntry
)
