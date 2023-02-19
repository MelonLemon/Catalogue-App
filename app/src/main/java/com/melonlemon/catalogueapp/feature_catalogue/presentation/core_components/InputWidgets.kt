package com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.presentation.home.HomeScreen
import com.melonlemon.catalogueapp.feature_catalogue.presentation.home.HomeViewModel
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInput(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChanged: (String) -> Unit,
    onCancelClicked: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onTextChanged,
        shape = MaterialTheme.shapes.extraSmall,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),
        placeholder = { Text(
            text= stringResource(R.string.search)
        ) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant) },
        trailingIcon = {
            if(text!=""){
                IconButton(
                    modifier = Modifier.background(
                        MaterialTheme.colorScheme.surface
                    ),
                    onClick = onCancelClicked
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_outline_cancel_24),
                        contentDescription = "Cancel",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UrlInput(
    modifier: Modifier = Modifier,
    urlText: String,
    onUrlChange: (String) -> Unit,
    onFolderBtnClick: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = urlText,
        onValueChange = onUrlChange,
        shape = MaterialTheme.shapes.extraSmall,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.search)
            )
        },
        trailingIcon = {
            Box(
                modifier = Modifier.padding(
                    end = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer
                        ),
                    onClick = onFolderBtnClick
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_folder_open_24),
                        contentDescription = "Cancel",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UrlInputWithCheckbox(
    modifier: Modifier = Modifier,
    checkedState: Boolean,
    title: String,
    urlText: String,
    onUrlChange: (String) -> Unit,
    onFolderBtnClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        CheckboxText(
            title = title,
            checkedState = checkedState
        )
        OutlinedTextField(
            value = urlText,
            onValueChange = onUrlChange,
            shape = MaterialTheme.shapes.extraSmall,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.search)
                )
            },
            trailingIcon = {
                Box(
                    modifier = Modifier.padding(
                        end = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer
                            ),
                        onClick = onFolderBtnClick
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_folder_open_24),
                            contentDescription = "Cancel",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchInputPreview() {
    CatalogueAppTheme{
        SearchInput(
            text = "example",
            onTextChanged = { },
            onCancelClicked = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UrlInputWithCheckboxPreview() {
    CatalogueAppTheme{
        UrlInputWithCheckbox(
            checkedState = false,
            title = "find Path",
            urlText = "",
            onFolderBtnClick = { },
            onUrlChange = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UrlInputPreview() {
    CatalogueAppTheme{
        UrlInput(
            urlText = "",
            onFolderBtnClick = { },
            onUrlChange = { }
        )
    }
}