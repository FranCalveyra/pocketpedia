package org.austral.pocketpedia.ui.shared.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage

@Composable
fun GifImage(
    resource: Int,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = resource,
        contentDescription = null,
        modifier = modifier
    )
}