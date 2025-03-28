package org.austral.pocketpedia.domain.models.pokemon

// Generated with quicktype.io
data class Pokemon(
    val id: Long,
    val name: String,
    val baseExperience: Long,
    val height: Long,
    val isDefault: Boolean,
    val order: Long,
    val weight: Long,
    val abilities: List<Ability>,
    val forms: List<Species>,
    val gameIndices: List<GameIndex>,
    val heldItems: List<HeldItem>,
    val locationAreaEncounters: String,
    val moves: List<Move>,
    val species: Species,
    val sprites: Sprites,
    val cries: Cries,
    val stats: List<Stat>,
    val types: List<Type>,
    val pastTypes: List<PastType>
)

data class Ability(
    val isHidden: Boolean,
    val slot: Long,
    val ability: Species
)

data class Species(
    val name: String,
    val url: String
)

data class Cries(
    val latest: String,
    val legacy: String
)

data class GameIndex(
    val gameIndex: Long,
    val version: Species
)

data class HeldItem(
    val item: Species,
    val versionDetails: List<VersionDetail>
)

data class VersionDetail(
    val rarity: Long,
    val version: Species
)

data class Move(
    val move: Species,
    val versionGroupDetails: List<VersionGroupDetail>
)

data class VersionGroupDetail(
    val levelLearnedAt: Long,
    val versionGroup: Species,
    val moveLearnMethod: Species
)

data class PastType(
    val generation: Species,
    val types: List<Type>
)

data class Type(
    val slot: Long,
    val type: Species
)

data class Generation5(
    val blackWhite: Sprites
)

data class Generation4(
    val diamondPearl: Sprites,
    val heartGoldSoulSilver: Sprites,
    val platinum: Sprites
)

data class Versions(
    val generation1: Generation1,
    val generation2: Generation2,
    val generation3: Generation3,
    val generation4: Generation4,
    val generation5: Generation5,
    val generationVi: Map<String, Home>,
    val generation7: Generation7,
    val generation8: Generation8
)

data class Other(
    val dreamWorld: DreamWorld,
    val home: Home,
    val officialArtwork: OfficialArtwork,
    val showdown: Sprites
)

data class Sprites(
    val backDefault: String,
    val backFemale: Any? = null,
    val backShiny: String,
    val backShinyFemale: Any? = null,
    val frontDefault: String,
    val frontFemale: Any? = null,
    val frontShiny: String,
    val frontShinyFemale: Any? = null,
    val other: Other? = null,
    val versions: Versions? = null,
    val animated: Sprites? = null
)

data class Generation1(
    val redBlue: RedBlue,
    val yellow: RedBlue
)

data class RedBlue(
    val backDefault: String,
    val backGray: String,
    val frontDefault: String,
    val frontGray: String
)

data class Generation2(
    val crystal: Crystal,
    val gold: Crystal,
    val silver: Crystal
)

data class Crystal(
    val backDefault: String,
    val backShiny: String,
    val frontDefault: String,
    val frontShiny: String
)

data class Generation3(
    val emerald: OfficialArtwork,
    val fireRedLeafGreen: Crystal,
    val rubySapphire: Crystal
)

data class OfficialArtwork(
    val frontDefault: String,
    val frontShiny: String
)

data class Home(
    val frontDefault: String,
    val frontFemale: Any? = null,
    val frontShiny: String,
    val frontShinyFemale: Any? = null
)

data class Generation7(
    val icons: DreamWorld,
    val ultraSunUltraMoon: Home
)

data class DreamWorld(
    val frontDefault: String,
    val frontFemale: Any? = null
)

data class Generation8(
    val icons: DreamWorld
)

data class Stat(
    val baseStat: Long,
    val effort: Long,
    val stat: Species
)

