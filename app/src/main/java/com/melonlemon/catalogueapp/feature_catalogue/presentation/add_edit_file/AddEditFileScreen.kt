package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file

import android.net.Uri
import android.widget.Space
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.CatalogueRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.*
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.CheckStatusAddStr
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TransactionCheckStatus
import com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util.AddEditFileEvents
import com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util.ColumnType
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.*
import com.melonlemon.catalogueapp.feature_catalogue.presentation.home.NewFolderEvents
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditFileScreen(
    saveBtnClick: () -> Unit,
    backBtnClick: () -> Unit,
    viewModel: AddEditFileViewModel
) {

    val tagsName = stringResource(R.string.tags)

    val addEditFileState by viewModel.addEditFileState.collectAsStateWithLifecycle()
    val fileInfo by viewModel.fileInfo.collectAsStateWithLifecycle()
    val columnsDialogState by viewModel.columnsDialogState.collectAsStateWithLifecycle()
    val saveNewFile by viewModel.saveNewFile.collectAsStateWithLifecycle()
    val fileColumns by viewModel.fileColumns.collectAsStateWithLifecycle()




    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if(uri!=null){
            viewModel.addEditFileEvents(AddEditFileEvents.OnUrlCoverImgChange(uri.toString()))
        }
    }

    if(saveNewFile){
        LaunchedEffect(saveNewFile){
            saveBtnClick()
        }
    }


    val columnsDialog = remember { mutableStateOf(false) }

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
            horizontalAlignment = Alignment.Start
        ) {
            item{
                BackArrowRow(
                    onArrowBackClick = backBtnClick,
                    title = stringResource(R.string.add_file)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
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
                    modifier = Modifier.fillMaxWidth(),
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
                    checkedState = addEditFileState.coverImgCheckStatus,
                    title = stringResource(R.string.cover_image),
                    urlText = fileInfo.coverImg ?:"",
                    onFolderBtnClick = {
                        launcher.launch("image/*")
                    },
                    onUrlChange = { urlString ->
                        viewModel.addEditFileEvents(
                            AddEditFileEvents.OnUrlCoverImgChange(urlString))
                    }
                )
            }
            item{
                if(fileInfo.coverImg!=""){
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(fileInfo.coverImg)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.placeholder),
                        error = painterResource(R.drawable.placeholder_error),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(200.dp)
                            .clip(MaterialTheme.shapes.small)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = "Photo File",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(200.dp)
                            .clip(MaterialTheme.shapes.small)
                    )
                }

            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item{
                Text(
                    text = stringResource(R.string.choose_title_column),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            item{
                val title = stringResource(R.string.title)
                LineClickTextCard(
                    modifier = Modifier.fillMaxWidth(),
                    text = fileColumns[fileInfo.titleColumnIndex].ifBlank { stringResource(R.string.no_title) },
                    onLineClick = {
                        viewModel.addEditFileEvents(
                            AddEditFileEvents.OnColumnTypeClick(
                                index = fileInfo.titleColumnIndex,
                                columnType = ColumnType.TitleColumn(title)
                            ))
                        columnsDialog.value = true
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item{
                Text(
                    text = stringResource(R.string.choose_subheader_column),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            item{
                val subHeader = stringResource(R.string.sub_header)
                LineClickTextCard(
                    modifier = Modifier.fillMaxWidth(),
                    text = fileColumns[fileInfo.subHeaderColumnIndex],
                    onLineClick = {
                        viewModel.addEditFileEvents(
                            AddEditFileEvents.OnColumnTypeClick(
                                index = fileInfo.subHeaderColumnIndex,
                                columnType = ColumnType.SubHeaderColumn(subHeader)
                            ))
                        columnsDialog.value = true
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item{
                Text(
                    text = stringResource(R.string.choose_cat_column),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            item{
                val category = stringResource(R.string.category)
                LineClickTextCard(
                    modifier = Modifier.fillMaxWidth(),
                    text = fileColumns[fileInfo.categoryColumnIndex].ifBlank { stringResource(R.string.no_title) },
                    onLineClick = {
                        viewModel.addEditFileEvents(
                            AddEditFileEvents.OnColumnTypeClick(
                                index = fileInfo.categoryColumnIndex,
                                columnType = ColumnType.CategoryColumn(category)
                            ))
                        columnsDialog.value = true
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item{
                Text(
                    text = stringResource(R.string.coverimage_for_records),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            item{
                val coverImgRec = stringResource(R.string.coverimage_for_records)
                LineClickTextCard(
                    modifier = Modifier.fillMaxWidth(),
                    text = if(fileInfo.covImgRecColumnIndex!=null)
                        fileColumns[fileInfo.covImgRecColumnIndex!!] else "",
                    onLineClick = {
                        viewModel.addEditFileEvents(
                            AddEditFileEvents.OnColumnTypeClick(
                                index = fileInfo.covImgRecColumnIndex?:0,
                                columnType = ColumnType.CovImgRecordColumn(coverImgRec)
                            ))
                        columnsDialog.value = true
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item{
                CheckboxText(
                    title = tagsName,
                    checkedState = addEditFileState.tagCheckStatus
                )
            }

            addCards(
                text = addEditFileState.newTag,
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
            item{
                Spacer(modifier = Modifier.height(100.dp))
            }

        }

        if(columnsDialog.value){
            ColumnsDialog(
                onSaveClick = { index ->
                    viewModel.addEditFileEvents(
                        AddEditFileEvents.OnSaveDialogClick(
                            index = index,
                            columnType = columnsDialogState.columnType
                    ))
                    columnsDialog.value = false
                },
                onCancelClick = {
                    columnsDialog.value = false
                },
                columnType = columnsDialogState.columnType,
                listColumn = fileColumns,
                selectedIndex = columnsDialogState.selectedIndex,
            )
        }
    }
}


