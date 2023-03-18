package com.melonlemon.catalogueapp.feature_catalogue.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.CatalogueRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.*
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.CheckStatusAddStr
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TransactionCheckStatus
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.BackArrowRow
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.LineTextCard
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.TextInputAdd
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFolderScreen(
    onArrowBackClick: () -> Unit,
    viewModel: HomeViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val foldersInfoState by viewModel.foldersInfoState.collectAsStateWithLifecycle()
    val deleteFolderCheckState by viewModel.deleteFolderCheckState.collectAsStateWithLifecycle()

    val errorMessages = mapOf(
        CheckStatusAddStr.BlankFailStatus to stringResource(R.string.empty_name),
        CheckStatusAddStr.DuplicateFailStatus to stringResource(R.string.duplicate_name),
        CheckStatusAddStr.SuccessStatus to stringResource(R.string.success),
        CheckStatusAddStr.UnKnownFailStatus to  stringResource(R.string.unknown_error)
    )
    val errorStatus = stringResource(R.string.unknown_error)

    if(foldersInfoState.folderAddStatus!= CheckStatusAddStr.UnCheckedStatus){

        LaunchedEffect(foldersInfoState.folderAddStatus){

            snackbarHostState.showSnackbar(
                message = errorMessages[foldersInfoState.folderAddStatus]?: errorStatus,
                actionLabel = null
            )
            viewModel.newFolderScreenEvents(NewFolderEvents.OnAddComplete)
        }
    }

    if(deleteFolderCheckState!= TransactionCheckStatus.UnCheckedStatus){
        val success = stringResource(R.string.success)
        val error = stringResource(R.string.unknown_error)

        LaunchedEffect(deleteFolderCheckState){

            snackbarHostState.showSnackbar(
                message = if(deleteFolderCheckState == TransactionCheckStatus.SuccessStatus)
                    success else error,
                actionLabel = null
            )
            viewModel.newFolderScreenEvents(NewFolderEvents.OnFolderDeleteComplete)
        }
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
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
                onArrowBackClick = onArrowBackClick,
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){

                itemsIndexed(foldersInfoState.listOfFolders){ index, folder ->
                    val dismissState = rememberDismissState(
                        confirmValueChange = {
                            if(it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart){
                                if(folder.id!=0){
                                    viewModel.newFolderScreenEvents(NewFolderEvents.DeleteFolder(folder.id))
                                }

                            }
                            true
                        }
                    )
                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val color=when(dismissState.dismissDirection){
                                DismissDirection.StartToEnd -> Color.Transparent
                                DismissDirection.EndToStart -> MaterialTheme.colorScheme.primaryContainer
                                null -> Color.Transparent
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Item",
                                    tint= Color.White,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }

                        },
                        dismissContent = {
                            LineTextCard(
                                modifier = Modifier.fillMaxWidth(),
                                text = folder.name
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                        },
                        directions=setOf(DismissDirection.EndToStart)
                    )

                }
            }

        }
    }
}

