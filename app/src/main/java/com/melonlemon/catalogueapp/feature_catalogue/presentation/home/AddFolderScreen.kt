package com.melonlemon.catalogueapp.feature_catalogue.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.BackArrowRow
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.LineTextCard
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.TextInputAdd
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFolderScreen(
    viewModel: HomeViewModel
) {
    val foldersInfoState by viewModel.foldersInfoState.collectAsStateWithLifecycle()

    Scaffold { it ->
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
                title = stringResource(R.string.folders)
            )
            TextInputAdd(
                text = foldersInfoState.newFolder,
                onTextChanged = { name ->
                    viewModel.newFolderScreenEvents(
                        NewFolderEvents.OnNameChanged(name)
                    )
                },
                placeholder = stringResource(R.string.columns),
                onAddBtnClick = {
                    viewModel.newFolderScreenEvents(
                        NewFolderEvents.OnAddButtonClick
                    )
                }
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ){

                items(foldersInfoState.listOfFolders){ folder ->
                    LineTextCard(
                        modifier = Modifier.fillMaxWidth(),
                        text = folder.name
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddFolderScreenPreview() {
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
            addNewRecord = AddNewRecord(repository),
            addTagRecord = AddTagRecord(repository)
        )
        val viewModel = HomeViewModel(useCases)
        AddFolderScreen(viewModel)
    }
}