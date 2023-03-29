package com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components

import android.graphics.drawable.Icon
import android.media.Image
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.TextView
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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.util.LinkifyCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme


@Composable
fun SmartCard(
    modifier: Modifier = Modifier,
    photo: String?=null,
    title: String,
    tags: List<String>,
    size: Int,
    onCardClick: () -> Unit,
    deleteEnabled: Boolean = false,
    isSelected: Boolean = false,
    onSelect: (Boolean) -> Unit
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
            if(photo!=null){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photo)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = (size - 12 * 2).dp, height = 200.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Photo File",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = (size - 12 * 2).dp, height = 200.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }


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
                        onClick = { }
                    )
                }
            }

            if(deleteEnabled){
                if(isSelected){
                    Button(
                        onClick = {onSelect(false)},
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                    ) {
                        Text(
                            text= stringResource(R.string.cancel),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                } else {
                    Button(
                        onClick = {onSelect(true)},
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Text(
                            text= stringResource(R.string.choose),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }


            }


        }
    }
}


@Composable
fun RecordSmartCard(
    modifier: Modifier = Modifier,
    photo: String?=null,
    title: String,
    subHeader: String,
    size: Int,
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
            if(photo!=null){
                val videoId = photo.replace("https://www.youtube.com/watch?v=", "")
                val photoDisplay = if(photo.contains("https://www.youtube.com/watch?v="))
                    "https://img.youtube.com/vi/$videoId/hqdefault.jpg" else photo
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photoDisplay)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = (size - 12 * 2).dp, height = 200.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Photo File",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = (size - 12 * 2).dp, height = 200.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }


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
                    text = title.ifBlank { stringResource(R.string.no_title) },
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End
                )
            }
            Text(
                text = subHeader.ifBlank { stringResource(R.string.no_subheader) },
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )

        }
    }
}

@Composable
fun DeleteCard(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
   Card(
       modifier = modifier,
       shape = MaterialTheme.shapes.medium,
       colors = CardDefaults.cardColors(
           containerColor = MaterialTheme.colorScheme.surface
       ),
       border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
   ) {
      Row(
          modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
      ){
          Button(
              onClick = onCancelClick,
              shape = MaterialTheme.shapes.extraLarge,
              colors = ButtonDefaults.buttonColors(
                  containerColor = MaterialTheme.colorScheme.surface
              ),
              border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
          ) {
            Text(
                text= stringResource(R.string.cancel),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Button(
              onClick = onDeleteClick,
              shape = MaterialTheme.shapes.extraLarge,
              colors = ButtonDefaults.buttonColors(
                  containerColor = MaterialTheme.colorScheme.primary
              ),
          ) {
              Text(
                  text= stringResource(R.string.delete),
                  color = MaterialTheme.colorScheme.onPrimary,
                  style = MaterialTheme.typography.labelLarge
              )
          }
      }
   }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HalfSmartCard(
    modifier: Modifier = Modifier,
    photo: String?=null,
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
            if(photo!=null){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photo)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = (size - 12 * 2).dp, height = 200.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Photo File",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = (size - 12 * 2).dp, height = 200.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }
            val tagsOG = tags.toMutableList()
            while(tagsOG.isNotEmpty()){
                Row(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TagButton(
                        name = tagsOG[0],
                        onClick = { }
                    )
                    tagsOG.removeFirst()
                    if(tagsOG.isNotEmpty()){
                        TagButton(
                            name = tagsOG[0],
                            onClick = { }
                        )
                        tagsOG.removeFirst()
                    }
                }
            }

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

@Composable
fun LineClickTextCard(
    modifier: Modifier=Modifier,
    text: String,
    onLineClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onLineClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_folder_open_24),
                contentDescription = "Cancel",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
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
}

@Composable
fun SelectLineTextCard(
    modifier: Modifier=Modifier,
    text: String,
    isSelected: Boolean = false,
    onCardClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onCardClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        )
    ){
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = modifier,
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                color = if(isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Left,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if(isSelected){
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = null // null recommended for accessibility with screenreaders
                )
            }
        }

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
        modifier = modifier
            .padding(16.dp)
            .pointerInput(Unit) {
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
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = modifier,
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Left,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            val mContext = LocalContext.current
            val mCustomLinkifyText = remember { TextView(mContext) }
            AndroidView(factory = { mCustomLinkifyText }) { textView ->
                textView.text = text
                textView.textSize = 20F
                textView.isSingleLine = false
                textView.setEllipsize(TextUtils.TruncateAt.END)
                LinkifyCompat.addLinks(textView, Linkify.ALL)
                textView.movementMethod = LinkMovementMethod.getInstance()
            }
        }


//        TextField(
//            value = text,
//            onValueChange = { text ->
//                if( enabled){
//                    onValueChange(text)
//                }
//            }
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun SmartCardPreview() {
    CatalogueAppTheme{
        SmartCard(
            photo= null,
            title="Korean food",
            tags = listOf("Spicy", "Chicken"),
            size=270,
            onCardClick = { },
            deleteEnabled = false,
            onSelect = { },
            isSelected = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HalfSmartCardPreview() {
    CatalogueAppTheme{
        HalfSmartCard(
            photo= null,
            tags = listOf("Spicy", "Chicken", "Spicy", "Chickennn"),
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