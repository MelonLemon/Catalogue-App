package com.melonlemon.catalogueapp.feature_catalogue.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.CatalogueRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.AddNewFolder
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.GetHomeScreenState
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.BasicButton
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.SearchInput
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.SmartCard
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchInput(
                modifier = Modifier.fillMaxWidth(),
                text = viewModel.homeScreenState.value.searchText,
                onTextChanged = { text ->
                    viewModel.homeScreenEvents(
                        HomeScreenEvents.OnSearchTextChanged(text))
                },
                onCancelClicked = {
                    viewModel.homeScreenEvents(
                        HomeScreenEvents.OnCancelSearchClick)
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LazyRow{
                    item{
                        BasicButton(
                            text = stringResource(R.string.all),
                            isSelected = viewModel.homeScreenState.value.selectedFolderId == -1,
                            onButtonClicked = {
                                HomeScreenEvents.OnCategoryClick(-1)
                            }
                        )
                    }
                    items(
                        items = viewModel.homeScreenState.value.listOfFolders,
                        key = { item ->
                            item.id
                        }
                    ) { item ->
                        BasicButton(
                            text = item.name,
                            isSelected = viewModel.homeScreenState.value.selectedFolderId == item.id,
                            onButtonClicked = {
                                HomeScreenEvents.OnCategoryClick(item.id)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.defaultMinSize(
                        minWidth = 40.dp
                    ),
                    shape = MaterialTheme.shapes.small,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    onClick = {
                        // Go to Folder Screen
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_folder_open_24),
                        contentDescription = "Folder",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }

            LazyColumn{
                items(
                    items = viewModel.homeScreenState.value.listOfFiles,
                    key = { item ->
                        item.id
                    }
                ){ file ->
                    SmartCard(
                        photo= null, //change
                        title=file.title,
                        tags = file.tags,
                        size=270,
                        onTagClick={ tag ->
                            HomeScreenEvents.AddTagToSearch(tag)
                        },
                        onCardClick = {
                            //navigationto File + file.id
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CatalogueAppTheme{
        val repository = CatalogueRepositoryImpl()
        val useCases = CatalogueUseCases(
            getHomeScreenState = GetHomeScreenState(repository),
            addNewFolder = AddNewFolder(repository)
        )
        val viewModel = HomeViewModel(useCases)
        HomeScreen(viewModel)
    }
}