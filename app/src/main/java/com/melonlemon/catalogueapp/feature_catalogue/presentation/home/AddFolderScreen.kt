package com.melonlemon.catalogueapp.feature_catalogue.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.AddButton
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.BackArrowRow
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.LineTextCard
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.TextInputAdd
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFolderScreen(
    viewModel: HomeViewModel
) {
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
                text = viewModel.newFolder.value,
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

                items(viewModel.homeScreenState.value.listOfFolders){ folder ->
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
            getHomeScreenState = GetHomeScreenState(repository),
            addNewFolder = AddNewFolder(repository)
        )
        val viewModel = HomeViewModel(useCases)
        AddFolderScreen(viewModel)
    }
}