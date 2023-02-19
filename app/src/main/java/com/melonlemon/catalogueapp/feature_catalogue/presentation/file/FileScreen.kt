package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.CatalogueRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.AddNewFolder
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.GetHomeScreenState
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.BackArrowRow
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.BasicButton
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.SearchInput
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.SmartCard
import com.melonlemon.catalogueapp.feature_catalogue.presentation.home.HomeScreenEvents
import com.melonlemon.catalogueapp.feature_catalogue.presentation.home.HomeViewModel
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileScreen(
    viewModel: FileViewModel
) {
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
                title = stringResource(R.string.folders)
            )
            SearchInput(
                modifier = Modifier.fillMaxWidth(),
                text = viewModel.fileScreenState.value.searchText,
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
                        isSelected = viewModel.fileScreenState.value.isAllTagsSelected,
                        onButtonClicked = {
                            viewModel.fileScreenEvents(
                                FileScreenEvents.OnAllTagsClick)
                        }
                    )
                }
                items(
                    items = viewModel.fileScreenState.value.listOfTags,
                    key = { item ->
                        item.id
                    }
                ) { item ->
                    BasicButton(
                        text = item.name,
                        isSelected = item.id in viewModel.fileScreenState.value.listOfSelectedTagsId,
                        onButtonClicked = {
                            viewModel.fileScreenEvents(
                                FileScreenEvents.OnTagClick(item.id))
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            LazyColumn{
                items(
                    items = viewModel.fileScreenState.value.listOfRecords,
                    key = { item ->
                        item.id
                    }
                ){ record ->
                    SmartCard(
                        photo= null, //change
                        title=record.title,
                        tags = record.tags,
                        size=270,
                        onTagClick={ tag ->
                            HomeScreenEvents.AddTagToSearch(tag)
                        },
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
            getHomeScreenState = GetHomeScreenState(repository),
            addNewFolder = AddNewFolder(repository)
        )
        val viewModel = FileViewModel(useCases)
        FileScreen(viewModel)
    }
}