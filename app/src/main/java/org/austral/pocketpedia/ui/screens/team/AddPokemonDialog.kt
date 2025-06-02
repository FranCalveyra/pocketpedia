package org.austral.pocketpedia.ui.screens.team

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.austral.pocketpedia.domain.models.pokemon.Pokemon
import org.austral.pocketpedia.domain.models.team.PokemonTeam

@Composable
fun AddPokemonDialog(
    teams: List<PokemonTeam>,
    searchResults: List<Pokemon>,
    query: String,
    onQueryChange: (String) -> Unit,
    selectedTeam: String,
    onTeamSelected: (String) -> Unit,
    newTeamName: String,
    onNewTeamNameChange: (String) -> Unit,
    onCreateTeam: () -> Unit,
    onPokemonClick: (Pokemon) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = if (teams.isEmpty()) "Create a Team" else "Add Pokémon")
        },
        text = {
            Column {
                // Always show team creation UI
                Text(text = "Create new team:")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = newTeamName,
                    onValueChange = onNewTeamNameChange,
                    placeholder = { Text(text = "Team Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = onCreateTeam,
                    enabled = newTeamName.isNotBlank()
                ) {
                    Text(text = "Create")
                }

                // If there are teams, show the add-Pokémon UI
                if (teams.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    // Search field
                    TextField(
                        value = query,
                        onValueChange = onQueryChange,
                        placeholder = { Text(text = "Search Pokémon") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Team selector
                    var expanded by remember { mutableStateOf(false) }
                    Text(
                        text = "Team: $selectedTeam",
                        modifier = Modifier
                            .clickable { expanded = true }
                            .padding(8.dp)
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        teams.forEach { team ->
                            DropdownMenuItem(onClick = {
                                onTeamSelected(team.teamName)
                                expanded = false
                            }) {
                                Text(text = team.teamName)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Search results
                    Column(
                        modifier = Modifier
                            .height(200.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        searchResults.forEach { pokemon ->
                            Text(
                                text = pokemon.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onPokemonClick(pokemon) }
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}