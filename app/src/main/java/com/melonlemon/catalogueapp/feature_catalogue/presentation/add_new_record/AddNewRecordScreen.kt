package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_new_record

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.CatalogueRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.*
import com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.AddEditFileEvents
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.*
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewRecordScreen(
    viewModel: AddNewRecordViewModel
) {

    val tagsName = stringResource(R.string.tags)

    val tagsRecordState by viewModel.tagsRecordState.collectAsStateWithLifecycle()
    val recordInfo by viewModel.recordInfo.collectAsStateWithLifecycle()
    val saveNewRecord by viewModel.saveNewRecord.collectAsStateWithLifecycle()

    LaunchedEffect(saveNewRecord){
        //navigation
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                onClick = {
                    viewModel.addEditRecordEvents(AddNewRecordEvents.OnSaveFabClick)
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
                    value = recordInfo.name,
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
                    urlText = recordInfo.urlString,
                    onFolderBtnClick = {
                        //open chooser
                    },
                    onUrlChange = { urlString ->
                        viewModel.addEditRecordEvents(AddNewRecordEvents.OnUrlPathChange(urlString))
                    }
                )
            }

            itemsIndexed(recordInfo.columnsInfo){ index, columnInfo ->
                OutlinedTextField(
                    value = columnInfo.text,
                    onValueChange = { text ->
                        viewModel.addEditRecordEvents(AddNewRecordEvents.OnColumnTextChange(
                            index = index,
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
                    checkedState = tagsRecordState.tagCheckStatus
                )
            }

            addCheckboxText(
                text = tagsRecordState.newTag,
                onTextChanged = { name ->
                    viewModel.addEditRecordEvents(
                        AddNewRecordEvents.OnNewTagNameChanged(name))
                },
                placeholder = tagsName,
                onAddBtnClick = {
                    viewModel.addEditRecordEvents(
                        AddNewRecordEvents.OnTagAddBtnClick)
                },
                listOfCheckboxText = tagsRecordState.listOfTags,
                listOfSelected = tagsRecordState.listOfSelectedTagsIndex,
                onCheckStateChange = { index, checkState ->
                    viewModel.addEditRecordEvents(
                        AddNewRecordEvents.OnCheckStateTagChange(
                            index, checkState
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
            getFolders = GetFolders(repository),
            addNewFolder = AddNewFolder(repository),
            getFilteredList = GetFilteredList(),
            getFiles = GetFiles(repository),
            getRecords = GetRecords(repository),
            getFileInfo = GetFileInfo(repository),
            updateRecord = UpdateRecord(repository),
            getRecord = GetRecord(repository),
            addNewFile = AddNewFile(repository),
            getFileColumns = GetFileColumns(repository),
            getTagsRecords = GetTagsRecords(repository),
            addNewRecord = AddNewRecord(repository)
        )
        val viewModel = AddNewRecordViewModel(useCases, SavedStateHandle())
        AddNewRecordScreen(viewModel)
    }
}