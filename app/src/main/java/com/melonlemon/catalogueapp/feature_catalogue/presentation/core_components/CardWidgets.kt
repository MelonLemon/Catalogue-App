package com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components

import android.media.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme


@Composable
fun SmartCard(
    modifier: Modifier = Modifier,
    photo: Painter?=null,
    title: String,
    tags: List<String>,
    size: Int,
    onTagClick: (String) -> Unit,
    onCardClick: () -> Unit
) {
    Card(
        modifier = modifier
            .width(size.dp)
            .clickable { onCardClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.End

        ) {
            Image(
                painter = photo ?: painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Photo File",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = (size - 12 * 2).dp, height = 200.dp)
                    .clip(MaterialTheme.shapes.small)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_message_24),
                    contentDescription = "Cancel",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant)

                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            LazyRow(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                contentPadding = PaddingValues(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tags){ tag ->
                    TagButton(
                        name = tag,
                        onClick = { onTagClick(tag) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HalfSmartCard(
    modifier: Modifier = Modifier,
    photo: Painter?=null,
    tags: List<String>,
    size: Int,
) {
    Card(
        modifier = modifier
            .width(size.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        val contentMaxWidth = size - 12 * 2
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.End

        ) {
            Image(
                painter = photo ?: painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Photo File",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = (contentMaxWidth).dp, height = 200.dp)
                    .clip(MaterialTheme.shapes.small)
            )

            //Tags - custom layout

        }
    }
}

@Composable
fun LineTextCard(
    modifier: Modifier=Modifier,
    text: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant)
    ){
        Text(
            modifier = modifier.padding(16.dp),
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Left,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiTextCard(
    modifier: Modifier=Modifier,
    title: String,
    text: String,
    onValueChange: (String) -> Unit
) {
    var enabled by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = { enabled = true },
                onTap = { /* Called on Tap */ }
            )
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant)
    ){
        Text(
            modifier = modifier.padding(16.dp),
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Left,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        TextField(
            value = text,
            onValueChange = { text ->
                if( enabled){
                    onValueChange(text)
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SmartCardPreview() {
    CatalogueAppTheme{
        SmartCard(
            photo= painterResource(id = R.drawable.ic_launcher_background),
            title="Korean food",
            tags = listOf("Spicy", "Chicken"),
            size=270,
            onTagClick={

            },
            onCardClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HalfSmartCardPreview() {
    CatalogueAppTheme{
        HalfSmartCard(
            photo= painterResource(id = R.drawable.ic_launcher_background),
            tags = listOf("Spicy", "Chicken", "Spicy", "Chicken"),
            size=270,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LineTextCardPreview() {
    CatalogueAppTheme{
        LineTextCard(
            text = "Movies"
        )
    }
}