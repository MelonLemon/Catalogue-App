package com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme

@Composable
fun BasicButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean = false,
    onButtonClicked: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onButtonClicked,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = if(isSelected) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if(isSelected) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.outline
        )
    ) {
        Text(
            modifier = Modifier.padding(0.dp),
            text = text,
            color = if(isSelected) MaterialTheme.colorScheme.onSecondaryContainer
            else MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.titleSmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
fun TagButton(
    modifier: Modifier = Modifier,
    name: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(
            start = 8.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
        modifier = Modifier.defaultMinSize(
            minWidth = 60.dp,
            minHeight = 30.dp
        ),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_tag_24),
            contentDescription = "Cancel",
            tint = MaterialTheme.colorScheme.onSecondaryContainer)

        Text(
            text = name,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit
) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        shape = MaterialTheme.shapes.small,
        onClick = onButtonClicked,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            modifier = Modifier.padding(0.dp),
            text = stringResource(R.string.add_btn).uppercase(),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BasicButtonPreview() {
    CatalogueAppTheme{
        BasicButton(
            text = "All",
            isSelected = true,
            onButtonClicked = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TagButtonPreview() {
    CatalogueAppTheme{
        TagButton(
            name = "Music",
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddButtonPreview() {
    CatalogueAppTheme{
        AddButton(
            onButtonClicked = { }
        )
    }
}