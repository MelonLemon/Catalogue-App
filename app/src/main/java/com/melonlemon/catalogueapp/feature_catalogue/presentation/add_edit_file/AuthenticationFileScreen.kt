package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.*
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TransactionCheckStatus
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.ValidationUrlCheckStatus
import com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util.*
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationFileScreen(
    nextBtnClick: () -> Unit,
    backBtn: () -> Unit,
    viewModel: AddEditFileViewModel
) {

    val snackbarHostState = remember { SnackbarHostState() }

    val errorMessages = mapOf(
        AuthenticationStatus.UnknownFailStatus to stringResource(R.string.unknown_error),
        AuthenticationStatus.NoRightsFailStatus to stringResource(R.string.no_rights_fail),
        AuthenticationStatus.NoNumberColumnStatus to stringResource(R.string.err_msg_column_num),
        AuthenticationStatus.SuccessStatus to stringResource(R.string.success),
        AuthenticationStatus.PathFailStatus to stringResource(R.string.path_fail_status)
    )

    val errorMessagesUrl = mapOf(
        ValidationUrlCheckStatus.BlankParameterFailStatus to stringResource(R.string.error_msg_sheets_id_name_empty),
        ValidationUrlCheckStatus.SuccessStatus to stringResource(R.string.success),
        ValidationUrlCheckStatus.NoRightsFailStatus to stringResource(R.string.err_msg_no_rights),
        ValidationUrlCheckStatus.BrokenUrlFailStatus to stringResource(R.string.err_msg_broken_url),
    )

    val errorStatus = stringResource(R.string.unknown_error)

    val authenticationState by viewModel.authenticationState.collectAsStateWithLifecycle()
    val fileColumns by viewModel.fileColumns.collectAsStateWithLifecycle()
    val fileInfo by viewModel.fileInfo.collectAsStateWithLifecycle()

    val rightsCheck by viewModel.rightsCheck.collectAsStateWithLifecycle()

    if(rightsCheck != ValidationUrlCheckStatus.UnCheckedStatus){

        LaunchedEffect(rightsCheck){


            snackbarHostState.showSnackbar(
                message = errorMessagesUrl[rightsCheck]?: errorStatus,
                actionLabel = null
            )
            viewModel.addEditFileEvents(AddEditFileEvents.OnRightCheckRefresh)
        }
    }


    if(authenticationState.authenticationStatus != AuthenticationStatus.UnCheckedStatus){
        LaunchedEffect(authenticationState.authenticationStatus){

            val authenticationStatus = authenticationState.authenticationStatus
            if(authenticationState.authenticationStatus != AuthenticationStatus.SuccessStatus){
                snackbarHostState.showSnackbar(
                    message = errorMessages[authenticationState.authenticationStatus]?: errorStatus,
                    actionLabel = null
                )
            }
            viewModel.addEditFileEvents(AddEditFileEvents.AuthenticationStatusRefresh)

            if(authenticationStatus == AuthenticationStatus.SuccessStatus){
                nextBtnClick()
            }
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
                        backBtn()
                    },
                    title = stringResource(R.string.add_file)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.End
                ){
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = fileInfo.sheetsId,
                        onValueChange = { idString ->
                            viewModel.addEditFileEvents(
                                AddEditFileEvents.OnSheetsIdChange(idString))
                        },
                        shape = MaterialTheme.shapes.extraSmall,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.type_link)
                            )
                        },
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = fileInfo.sheetsName,
                        onValueChange = { name ->
                            viewModel.addEditFileEvents(
                                AddEditFileEvents.OnSheetsNameChange(name))
                        },
                        shape = MaterialTheme.shapes.extraSmall,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.google_sheet_name)
                            )
                        },
                    )
                    Button(
                        contentPadding = PaddingValues(8.dp),
                        shape = MaterialTheme.shapes.small,
                        onClick = {
                            viewModel.addEditFileEvents(AddEditFileEvents.OnPathCheckBtnClick)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(0.dp),
                            text = stringResource(R.string.btn_check).uppercase(),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1
                        )
                    }
                }

            }
            item{
                CheckboxText(
                    title = stringResource(R.string.rights),
                    checkedState = authenticationState.checkStatusRights
                )
            }
            item {
                MultiTextCard(
                    title = if(authenticationState.checkStatusRights) stringResource(R.string.access)
                    else "No Access",
                    text = if(authenticationState.checkStatusRights) stringResource(R.string.access_desc)
                    else stringResource(R.string.no_permission_detect),
                    onValueChange = { }
                )
            }
            if(authenticationState.checkStatusRights){
                item{
                    CheckboxText(
                        title = stringResource(R.string.num_columns),
                        checkedState = authenticationState.checkStatusNumColumn
                    )
                }
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        OutlinedTextField(
                            modifier = Modifier.width(100.dp),
                            value = if(fileInfo.numColumns!=0) fileInfo.numColumns.toString()
                            else "",
                            onValueChange = { numberString ->
                                viewModel.addEditFileEvents(
                                    AddEditFileEvents.OnNumColumnChange(
                                        numberString.toIntOrNull() ?:0
                                    )
                                )
                            },
                            shape = MaterialTheme.shapes.extraSmall,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.placeholder_num_columns)
                                )
                            },
                        )
                        Button(
                            contentPadding = PaddingValues(8.dp),
                            shape = MaterialTheme.shapes.small,
                            onClick = {
                                      viewModel.addEditFileEvents(AddEditFileEvents.OnColumnCheckBtnClick)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                modifier = Modifier.padding(0.dp),
                                text = stringResource(R.string.btn_check).uppercase(),
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1
                            )
                        }
                    }
                }
                items(fileColumns){ name ->
                    LineTextCard(
                        modifier = Modifier.fillMaxWidth(),
                        text = name
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

