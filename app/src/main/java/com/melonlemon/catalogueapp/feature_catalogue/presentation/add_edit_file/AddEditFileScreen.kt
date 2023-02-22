package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.CatalogueRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.*
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.*
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditFileScreen(
    viewModel: AddEditFileViewModel
) {
    val columnsName = stringResource(R.string.columns)
    val tagsName = stringResource(R.string.tags)

    val addEditFileState by viewModel.addEditFileState.collectAsStateWithLifecycle()
    val fileInfo by viewModel.fileInfo.collectAsStateWithLifecycle()

    val saveNewFile by viewModel.saveNewFile.collectAsStateWithLifecycle()

    LaunchedEffect(saveNewFile){
        //navigation
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                onClick = {
                    viewModel.addEditFileEvents(AddEditFileEvents.OnSaveFabClick)
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
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item{
                BackArrowRow(
                    onArrowBackClick = {
                        // Navigation
                    },
                    title = stringResource(R.string.folders)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    items(
                        items = addEditFileState.listOfFolders,
                        key = { item ->
                            item.id
                        }
                    ) { item ->
                        BasicButton(
                            text = item.name,
                            isSelected = item.id == fileInfo.folderId,
                            onButtonClicked = {
                                viewModel.addEditFileEvents(
                                    AddEditFileEvents.OnFolderClick(item.id))
                            }
                        )
                    }
                }
            }
            item{
                OutlinedTextField(
                    value = fileInfo.name,
                    onValueChange = { name ->
                        viewModel.addEditFileEvents(
                            AddEditFileEvents.OnNameChanged(name))
                    },
                    shape = MaterialTheme.shapes.extraSmall,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    placeholder = { Text(
                        text= stringResource(R.string.name))
                    })
            }
            item {
                UrlInputWithCheckbox(
                    modifier = Modifier.fillMaxWidth(),
                    checkedState = addEditFileState.pathCheckStatus,
                    title = stringResource(R.string.file_path),
                    urlText = fileInfo.urlPath,
                    onFolderBtnClick = {
                             //open chooser
                    },
                    onUrlChange = { urlString ->
                        viewModel.addEditFileEvents(
                            AddEditFileEvents.OnUrlPathChange(urlString))
                    }
                )
            }
            item {
                UrlInputWithCheckbox(
                    modifier = Modifier.fillMaxWidth(),
                    checkedState = addEditFileState.coverImgCheckStatus,
                    title = stringResource(R.string.cover_image),
                    urlText = fileInfo.urlCoverImage,
                    onFolderBtnClick = {
                        //open chooser
                    },
                    onUrlChange = { urlString ->
                        viewModel.addEditFileEvents(
                            AddEditFileEvents.OnUrlCoverImgChange(urlString))
                    }
                )
            }

            item{
                LineTextCard(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.link_column)
                )
            }


            addCards(
                text = viewModel.addEditFileState.value.newColumn,
                onTextChanged = { name ->
                    viewModel.addEditFileEvents(
                        AddEditFileEvents.OnNewColumnNameChanged(name))
                },
                placeholder = columnsName,
                onAddBtnClick = {
                    viewModel.addEditFileEvents(
                        AddEditFileEvents.OnColumnAddBtnClick)
                },
                listOfCards = fileInfo.columns
            )

            item{
                CheckboxText(
                    title = tagsName,
                    checkedState = addEditFileState.tagCheckStatus
                )
            }

            addCards(
                text = viewModel.addEditFileState.value.newTag,
                onTextChanged = { name ->
                    viewModel.addEditFileEvents(
                        AddEditFileEvents.OnNewTagNameChanged(name))
                },
                placeholder = tagsName,
                onAddBtnClick = {
                    viewModel.addEditFileEvents(
                        AddEditFileEvents.OnTagAddBtnClick)
                },
                listOfCards = fileInfo.tags
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddEditFileScreenPreview() {
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
        val viewModel = AddEditFileViewModel(useCases)
        AddEditFileScreen(viewModel)
    }
}