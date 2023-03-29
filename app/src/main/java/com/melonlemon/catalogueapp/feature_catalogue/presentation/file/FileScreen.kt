package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.*
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.ValidationUrlCheckStatus
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileScreen(
    backBtnClick: () -> Unit,
    onRecordClick: () -> Unit,
    onEditClick: (fileId: Int) -> Unit,
    viewModel: FileViewModel
) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val fileInfoState by viewModel.fileInfoState.collectAsStateWithLifecycle()
    val forRecordsState by viewModel.forRecordsState.collectAsStateWithLifecycle()
    val listOfRecords by viewModel.listOfRecords.collectAsStateWithLifecycle()
    val isDownloading by viewModel.isDownloading.collectAsStateWithLifecycle()
    val recordClick by viewModel.recordClick.collectAsStateWithLifecycle()


    if(recordClick){
        LaunchedEffect(recordClick){
            viewModel.fileScreenEvents(FileScreenEvents.RecordClickRefresh)
            onRecordClick()
        }
    }

    Scaffold() { it ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                BackArrowRow(
                    modifier = Modifier.weight(0.7f),
                    onArrowBackClick = backBtnClick,
                    title = fileInfoState.title
                )
                IconButton(
                    modifier = Modifier.weight(0.3f),
                    onClick = { onEditClick(forRecordsState.fileId) },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.onBackground)
                }
            }

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

            if(isDownloading){
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                if(listOfRecords.validationUrlCheckStatus != ValidationUrlCheckStatus.SuccessStatus){
                    when(listOfRecords.validationUrlCheckStatus) {
                        is ValidationUrlCheckStatus.NoRightsFailStatus -> {
                            ErrorMsgView(
                                text = stringResource(R.string.valid_msg_no_rights),
                                imageBitmap = ImageBitmap.imageResource(id = R.drawable.goodies_nope)
                            )
                        }
                        is ValidationUrlCheckStatus.BrokenUrlFailStatus -> {
                            ErrorMsgView(
                                text = stringResource(R.string.valid_msg_broken_url),
                                imageBitmap = ImageBitmap.imageResource(id = R.drawable.goodies_error)
                            )
                        }
                        else -> {
                            ErrorMsgView(
                                text = stringResource(R.string.valid_msg_general),
                                imageBitmap = ImageBitmap.imageResource(id = R.drawable.goodies_error)
                            )
                        }
                    }

                } else {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ){
                        item{
                            BasicButton(
                                text = stringResource(R.string.all),
                                isSelected = forRecordsState.isAllCategoriesSelected,
                                onButtonClicked = {
                                    viewModel.fileScreenEvents(
                                        FileScreenEvents.OnAllTagsClick)
                                }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        itemsIndexed(fileInfoState.listOfCategories) { _, item ->
                            BasicButton(
                                text = item.ifBlank { stringResource(R.string.no_category) },
                                isSelected = if(forRecordsState.isAllCategoriesSelected) false
                                else item in forRecordsState.listOfSelectedCategories,
                                onButtonClicked = {
                                    viewModel.fileScreenEvents(FileScreenEvents.OnTagClick(item))
                                }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ){
                            if(listOfRecords.records != null){
                                val sizeRecords = listOfRecords.records!!.size
                                //RecordSmartCard as well get thumbnail from standard Youtube Link
                                items(sizeRecords){ index ->
                                    val singleRecord = listOfRecords.records!![index]
                                    if(singleRecord.isNotEmpty()){
                                        val photoLink = if(forRecordsState.covImgRecordsIndex!=null && singleRecord.indices.contains(forRecordsState.covImgRecordsIndex!!))
                                            singleRecord[forRecordsState.covImgRecordsIndex!!] else null
                                        RecordSmartCard(
                                            title = if(singleRecord.indices.contains(forRecordsState.titleColumnIndex))
                                                singleRecord[forRecordsState.titleColumnIndex] else stringResource(R.string.no_title),
                                            subHeader = if(singleRecord.indices.contains(forRecordsState.subHeaderColumnIndex))
                                                singleRecord[forRecordsState.subHeaderColumnIndex] else stringResource(R.string.no_subheader),
                                            size=270,
                                            photo = photoLink,
                                            onCardClick = {
                                                viewModel.fileScreenEvents(FileScreenEvents.OnRecordSelect(index))
                                            }
                                        )
                                    }
                                }
                            } else {
                                item{
                                    ErrorMsgView(
                                        text = stringResource(R.string.no_records),
                                        imageBitmap = ImageBitmap.imageResource(id = R.drawable.goodies_warning)
                                    )
                                }
                            }

                        }

                }

            }
        }
    }
}


