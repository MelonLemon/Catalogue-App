package com.melonlemon.catalogueapp.feature_catalogue.presentation.home

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.*
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TransactionCheckStatus
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.BasicButton
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.DeleteCard
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.SearchInput
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.SmartCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddNewFileClick: () -> Unit,
    onFolderBtnClick: () -> Unit,
    onFileClick: (fileId: Int, title: String) -> Unit,
    viewModel: HomeViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val selectedFolderId by viewModel.selectedFolderId.collectAsStateWithLifecycle()
    val foldersInfoState by viewModel.foldersInfoState.collectAsStateWithLifecycle()
    val listOfFiles by viewModel.listOfFiles.collectAsStateWithLifecycle()
    val deleteState by viewModel.deleteState.collectAsStateWithLifecycle()
    val isDownloading by viewModel.isDownloading.collectAsStateWithLifecycle()

    val errorMessages = mapOf(
        TransactionCheckStatus.BlankParameterFailStatus to stringResource(R.string.empty_name),
        TransactionCheckStatus.SuccessStatus to stringResource(R.string.success),
        TransactionCheckStatus.UnKnownFailStatus to  stringResource(R.string.unknown_error)
    )
    val errorStatus = stringResource(R.string.unknown_error)
    val density = LocalDensity.current

    if(deleteState.deleteCheckStatus!= TransactionCheckStatus.UnCheckedStatus){

        LaunchedEffect(deleteState.deleteCheckStatus){

            snackbarHostState.showSnackbar(
                message = errorMessages[deleteState.deleteCheckStatus]?: errorStatus,
                actionLabel = null,
                duration = SnackbarDuration.Short
            )
            viewModel.homeScreenEvents(HomeScreenEvents.OnDeleteCompleteClick)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        floatingActionButton = {
            AnimatedVisibility(visible = !deleteState.deleteState){
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = onAddNewFileClick,
                ) {
                    Icon(Icons.Filled.Add, "Localized description")
                }
            }

        },
        bottomBar = {
            AnimatedVisibility(
                visible = deleteState.deleteState,
                enter = slideInVertically {
                    with(density) { 40.dp.roundToPx() }
                } + expandVertically(
                    expandFrom = Alignment.Bottom
                ) + fadeIn(
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ){
                DeleteCard(
                    modifier = Modifier.fillMaxWidth(),
                    onCancelClick = {
                        viewModel.homeScreenEvents(HomeScreenEvents.OnCancelBtnClick)
                    },
                    onDeleteClick = {
                        viewModel.homeScreenEvents(HomeScreenEvents.OnDeleteBtnClick)
                    }
                )
            }
        }
    ) { it ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it).padding(top=16.dp, start=16.dp, end=16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchInput(
                modifier = Modifier.fillMaxWidth(),
                text = searchText,
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
                LazyRow(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ){
                    item{
                        BasicButton(
                            text = stringResource(R.string.all),
                            isSelected = selectedFolderId == -1,
                            onButtonClicked = {
                                viewModel.homeScreenEvents(
                                    HomeScreenEvents.OnCategoryClick(-1))
                            }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }

                    itemsIndexed(foldersInfoState.listOfFolders.filter { it.id != viewModel.constantFolderId}){ index, item ->
                        BasicButton(
                            text = item.name,
                            isSelected = selectedFolderId == item.id,
                            onButtonClicked = {
                                viewModel.homeScreenEvents(
                                    HomeScreenEvents.OnCategoryClick(item.id))
                            }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    if(foldersInfoState.listOfFolders.filter { it.id == viewModel.constantFolderId }.isNotEmpty()){
                        item{
                            BasicButton(
                                text = foldersInfoState.listOfFolders.filter { it.id == viewModel.constantFolderId }[0].name,
                                isSelected = selectedFolderId == viewModel.constantFolderId,
                                onButtonClicked = {
                                    viewModel.homeScreenEvents(
                                        HomeScreenEvents.OnCategoryClick(viewModel.constantFolderId))
                                }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
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
                    onClick = onFolderBtnClick
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_folder_open_24),
                        contentDescription = "Folder",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.Start
            ){
                Button(
                    modifier = Modifier.defaultMinSize(
                        minWidth = 40.dp
                    ),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    onClick = {
                        viewModel.homeScreenEvents(HomeScreenEvents.OnDeleteToggleBtnClick(!deleteState.deleteState))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = if(deleteState.deleteState) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onBackground)
                }
                Text(
                    text= stringResource(R.string.files_titles)
                )
            }

            if(isDownloading) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    items(
                        items = listOfFiles,
                        key = { item ->
                            item.id
                        }
                    ){ file ->
                        SmartCard(
                            photo= file.photoPath,
                            title=file.title,
                            tags = file.tags,
                            size=270,
                            onCardClick = {
                                onFileClick(
                                    file.id,
                                    file.title
                                )
                            },
                            deleteEnabled = deleteState.deleteState,
                            onSelect = { isSelected ->
                                viewModel.homeScreenEvents(
                                    HomeScreenEvents.OnDeleteChosenClick(
                                        id = file.id,
                                        status = isSelected
                                    ))
                            },
                            isSelected = file.id in deleteState.deleteList
                        )
                    }
                }
            }

        }
    }
}


