package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.BackArrowRow
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.BasicButton
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.SearchInput
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.SmartCard
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileScreen(
    viewModel: FileViewModel
) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val fileInfoState by viewModel.fileInfoState.collectAsStateWithLifecycle()
    val forRecordsState by viewModel.forRecordsState.collectAsStateWithLifecycle()
    val listOfRecords by viewModel.listOfRecords.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                onClick = {
                    //Navigation
                },
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
    ) { it ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BackArrowRow(
                onArrowBackClick = {
                    // Navigation
                },
                title = fileInfoState.title
            )
            SearchInput(
                modifier = Modifier.fillMaxWidth(),
                text = searchText,
                onTextChanged = { text ->
                    viewModel.fileScreenEvents(
                        FileScreenEvents.OnSearchTextChanged(text))
                },
                onCancelClicked = {
                    viewModel.fileScreenEvents(
                        FileScreenEvents.OnCancelSearchClick)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                item{
                    BasicButton(
                        text = stringResource(R.string.all),
                        isSelected = forRecordsState.isAllTagsSelected,
                        onButtonClicked = {
                            viewModel.fileScreenEvents(
                                FileScreenEvents.OnAllTagsClick)
                        }
                    )
                }
                itemsIndexed(fileInfoState.listOfTags) { _,item ->
                    BasicButton(
                        text = item,
                        isSelected = item in forRecordsState.listOfSelectedTags,
                        onButtonClicked = {
                            viewModel.fileScreenEvents(FileScreenEvents.OnTagClick(item))
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            LazyColumn{
                items(
                    items = listOfRecords,
                    key = { item ->
                        item.id
                    }
                ){ record ->
                    SmartCard(
                        photo= null, //change
                        title=record.title,
                        tags = record.tags,
                        size=270,
                        onCardClick = {
                            //navigation to Record + record.id
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FileScreenPreview() {
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
        val viewModel = FileViewModel(useCases, SavedStateHandle())
        FileScreen(viewModel)
    }
}