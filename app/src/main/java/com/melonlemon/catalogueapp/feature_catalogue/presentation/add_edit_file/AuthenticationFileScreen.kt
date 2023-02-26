package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.CatalogueRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.*
import com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util.*
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.*
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationFileScreen(
    viewModel: AddEditFileViewModel
) {

    val snackbarHostState = remember { SnackbarHostState() }

    val errorMessages = mapOf(
        AuthenticationStatus.UnknownFailStatus to stringResource(R.string.unknown_error),
        AuthenticationStatus.NoRightsFailStatus to stringResource(R.string.no_rights_fail),
        AuthenticationStatus.FileNameFailStatus to stringResource(R.string.file_name_fail),
        AuthenticationStatus.SuccessStatus to stringResource(R.string.success),
        AuthenticationStatus.PathFailStatus to stringResource(R.string.path_fail_status)
    )
    val errorStatus = stringResource(R.string.unknown_error)

    val permissionLevel = mapOf(
        PermissionLevel.Owner to Pair("Owner", stringResource(R.string.unknown_error)),
        PermissionLevel.Editor to Pair("Editor", stringResource(R.string.unknown_error)),
        PermissionLevel.Commenter to Pair("Commenter", stringResource(R.string.unknown_error)),
        PermissionLevel.Viewer to Pair(stringResource(R.string.unknown_error), stringResource(R.string.unknown_error)),
    )

    val visibilityLevel = mapOf(
        VisibilityLevel.PublicType to stringResource(R.string.unknown_error),
        VisibilityLevel.PublicLinkRestrictType to stringResource(R.string.no_rights_fail),
        VisibilityLevel.PrivateType to stringResource(R.string.file_name_fail),
    )

    val addEditFileState by viewModel.addEditFileState.collectAsStateWithLifecycle()
    val fileInfo by viewModel.fileInfo.collectAsStateWithLifecycle()
    val authenticationState by viewModel.authenticationState.collectAsStateWithLifecycle()
    val saveNewFile by viewModel.saveNewFile.collectAsStateWithLifecycle()

    if(authenticationState.authenticationStatus != AuthenticationStatus.UnCheckedStatus)
    LaunchedEffect(saveNewFile){

        snackbarHostState.showSnackbar(
            message = errorMessages[authenticationState.authenticationStatus]?: errorStatus,
            actionLabel = null
        )
        viewModel.addEditFileEvents(AddEditFileEvents.AuthenticationStatusRefresh)

        if(authenticationState.authenticationStatus== AuthenticationStatus.SuccessStatus){
            //navigation
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomBar = {
            Button(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                shape = MaterialTheme.shapes.small,
                onClick = {
                    viewModel.addEditFileEvents(AddEditFileEvents.OnNextBtnClick)

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    modifier = Modifier.padding(0.dp),
                    text = stringResource(R.string.next).uppercase(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )
            }
        }
    ) { it ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                BackArrowRow(
                    onArrowBackClick = {
                        // Navigation
                    },
                    title = stringResource(R.string.add_file)
                )
                Spacer(modifier = Modifier.width(8.dp))
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
            item{
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    BasicButton(
                        text = stringResource(R.string.new_file),
                        isSelected =  authenticationState.typeFile == TypeFile.NewFile,
                        onButtonClicked = {
                            viewModel.addEditFileEvents(
                                AddEditFileEvents.OnNewExistingFileClick(TypeFile.NewFile))
                        }
                    )
                    BasicButton(
                        text = stringResource(R.string.existing_file),
                        isSelected =  authenticationState.typeFile == TypeFile.ExistingFile,
                        onButtonClicked = {
                            viewModel.addEditFileEvents(
                                AddEditFileEvents.OnNewExistingFileClick(TypeFile.ExistingFile))
                        }
                    )
                }
            }
            if(authenticationState.typeFile == TypeFile.NewFile){
                item {
                    UrlInputWithCheckbox(
                        modifier = Modifier.fillMaxWidth(),
                        checkedState = authenticationState.pathCheckStatusNew,
                        title = stringResource(R.string.choose_folder),
                        urlText = authenticationState.urlPathFolderNew,
                        onFolderBtnClick = {
                            //open chooser
                        },
                        onUrlChange = { urlString ->
                            viewModel.addEditFileEvents(
                                AddEditFileEvents.UrlPathFolderNewChange(urlString))
                        }
                    )
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = if(authenticationState.pathCheckStatusNew) ""
                        else stringResource(R.string.path_folder_fail),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                item {
                    OutlinedTextField(
                        value = authenticationState.urlPathNameNew,
                        onValueChange = { name ->
                            viewModel.addEditFileEvents(
                                AddEditFileEvents.UrlPathNameNewChange(name))
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

                item{
                    CheckboxText(
                        title = stringResource(R.string.rights),
                        checkedState = true
                    )
                }
                item{
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ){
                        BasicButton(
                            text = stringResource(R.string.public_link_type),
                            isSelected =  authenticationState.visibilityLevelNew == VisibilityLevel.PublicLinkRestrictType,
                            onButtonClicked = {
                                viewModel.addEditFileEvents(
                                    AddEditFileEvents.OnPublicPrivateRightsClick(VisibilityLevel.PublicLinkRestrictType))
                            }
                        )
                        BasicButton(
                            text = stringResource(R.string.private_type),
                            isSelected =  authenticationState.visibilityLevelNew == VisibilityLevel.PrivateType,
                            onButtonClicked = {
                                viewModel.addEditFileEvents(
                                    AddEditFileEvents.OnPublicPrivateRightsClick(VisibilityLevel.PrivateType))
                            }
                        )
                    }
                }
                item{
                    MultiTextCard(
                        title = stringResource(R.string.visibility),
                        text = visibilityLevel[authenticationState.visibilityLevelNew]?: "",
                        onValueChange = { }
                    )
                }
                item{
                    TextInputAdd(
                        text = authenticationState.newEmailNewFile,
                        placeholder = stringResource(R.string.email),
                        onTextChanged = { name ->
                            viewModel.addEditFileEvents(AddEditFileEvents.OnNewEmailChanged(name))
                        },
                        onAddBtnClick = {
                            viewModel.addEditFileEvents(AddEditFileEvents.OnNewEmailAddBtnClick)
                        },
                        keyboardType = KeyboardType.Email
                    )
                }
                item{
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ){
                        BasicButton(
                            text = stringResource(R.string.permission_lvl_viewer),
                            isSelected =  authenticationState.newPermissionLevel == PermissionLevel.Viewer,
                            onButtonClicked = {
                                viewModel.addEditFileEvents(
                                    AddEditFileEvents.OnNewPermissionLevelClick(PermissionLevel.Viewer))
                            }
                        )
                        BasicButton(
                            text = stringResource(R.string.permission_lvl_commenter),
                            isSelected =  authenticationState.newPermissionLevel == PermissionLevel.Commenter,
                            onButtonClicked = {
                                viewModel.addEditFileEvents(
                                    AddEditFileEvents.OnNewPermissionLevelClick(PermissionLevel.Commenter))
                            }
                        )
                        BasicButton(
                            text = stringResource(R.string.permission_lvl_editor),
                            isSelected =  authenticationState.newPermissionLevel == PermissionLevel.Editor,
                            onButtonClicked = {
                                viewModel.addEditFileEvents(
                                    AddEditFileEvents.OnNewPermissionLevelClick(PermissionLevel.Editor))
                            }
                        )
                    }
                }
                val listEmails: List<String> = when(authenticationState.newPermissionLevel){
                    is PermissionLevel.Viewer -> {
                        authenticationState.emailViewerList
                    }
                    is PermissionLevel.Commenter -> {
                        authenticationState.emailCommenterList
                    }
                    is PermissionLevel.Editor -> {
                        authenticationState.emailEditorList
                    }
                    else -> {
                        emptyList<String>()
                    }
                }
                items(listEmails){ card ->
                    LineTextCard(
                        modifier = Modifier.fillMaxWidth(),
                        text = card
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
            if(authenticationState.typeFile == TypeFile.ExistingFile){
                item {
                    UrlInputWithCheckbox(
                        modifier = Modifier.fillMaxWidth(),
                        checkedState = authenticationState.pathCheckStatusExist,
                        title = stringResource(R.string.choose_folder),
                        urlText = authenticationState.fullUrlExistingFile,
                        onFolderBtnClick = {
                            //open chooser
                        },
                        onUrlChange = { urlString ->
                            viewModel.addEditFileEvents(
                                AddEditFileEvents.FullUrlExistingFileChange(urlString))
                        }
                    )
                    Text(
                        text = if(authenticationState.pathCheckStatusNew) ""
                        else stringResource(R.string.path_folder_fail),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                item{
                    CheckboxText(
                        title = stringResource(R.string.rights),
                        checkedState = authenticationState.checkStatusRightsExist
                    )
                }
                if(authenticationState.checkStatusRightsExist){
                    item {
                        MultiTextCard(
                            title = permissionLevel[authenticationState.permissionLevelExist]?.first ?: "",
                            text = permissionLevel[authenticationState.permissionLevelExist]?.second ?: "",
                            onValueChange = { }
                        )
                    }
                } else {
                    item{
                        MultiTextCard(
                            title = "",
                            text = stringResource(R.string.no_permission_detect),
                            onValueChange = { }
                        )
                    }

                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FileAuthenticationScreenPreview() {
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
        AuthenticationFileScreen(viewModel)
    }
}