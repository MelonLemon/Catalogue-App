package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_new_record

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.CatalogueRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.AddNewFolder
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.GetHomeScreenState
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.*
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewRecordScreen(
    viewModel: AddNewRecordViewModel
) {
    val columnsName = stringResource(R.string.columns)
    val tagsName = stringResource(R.string.tags)
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                onClick = {
                    //Navigation
                },
            ) {
                Icon(Icons.Filled.Done, "Localized description")
            }
        }
    ) { it ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item{
                BackArrowRow(
                    onArrowBackClick = {
                        // Navigation
                    },
                    title = stringResource(R.string.record)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            item{
                OutlinedTextField(
                    value = viewModel.addEditRecordState.value.name,
                    onValueChange = { name ->
                        viewModel.addEditRecordEvents(AddNewRecordEvents.OnNameChange(name))
                    },
                    shape = MaterialTheme.shapes.extraSmall,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    placeholder = { Text(
                        text= stringResource(R.string.name)
                    ) },
                )
            }
            item{
                UrlInput(
                    modifier = Modifier.fillMaxWidth(),
                    urlText = viewModel.addEditRecordState.value.urlRecord,
                    onFolderBtnClick = {
                        //open chooser
                    },
                    onUrlChange = { urlString ->
                        viewModel.addEditRecordEvents(AddNewRecordEvents.OnUrlPathChange(urlString))
                    }
                )
            }

            items(viewModel.addEditRecordState.value.listOfColumns){ columnInfo ->
                OutlinedTextField(
                    value = columnInfo.text,
                    onValueChange = { text ->
                        viewModel.addEditRecordEvents(AddNewRecordEvents.OnColumnTextChange(
                            id = columnInfo.id,
                            text = text
                        ))
                    },
                    shape = MaterialTheme.shapes.extraSmall,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    placeholder = { Text(
                        text= columnInfo.label
                    ) },
                )

            }

            item{
                CheckboxText(
                    title = tagsName,
                    checkedState = viewModel.addEditRecordState.value.tagCheckStatus
                )
            }

            addCheckboxText(
                text = viewModel.addEditRecordState.value.newTag,
                onTextChanged = { name ->
                    viewModel.addEditRecordEvents(
                        AddNewRecordEvents.OnNewTagNameChanged(name))
                },
                placeholder = tagsName,
                onAddBtnClick = {
                    viewModel.addEditRecordEvents(
                        AddNewRecordEvents.OnTagAddBtnClick)
                },
                listOfCheckboxText = viewModel.addEditRecordState.value.listOfTags,
                onCheckStateChange = { id, checkState ->
                    viewModel.addEditRecordEvents(
                        AddNewRecordEvents.OnCheckStateTagChange(
                            id, checkState
                        ))
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddNewFileScreenPreview() {
    CatalogueAppTheme{
        val repository = CatalogueRepositoryImpl()
        val useCases = CatalogueUseCases(
            getHomeScreenState = GetHomeScreenState(repository),
            addNewFolder = AddNewFolder(repository)
        )
        val viewModel = AddNewRecordViewModel(useCases)
        AddNewRecordScreen(viewModel)
    }
}