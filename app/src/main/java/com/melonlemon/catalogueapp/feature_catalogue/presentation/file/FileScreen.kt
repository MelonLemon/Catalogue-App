package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.CatalogueRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.*
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.*
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileScreen(
    backBtnClick: () -> Unit,
    onRecordClick: () -> Unit,
    viewModel: FileViewModel
) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val fileInfoState by viewModel.fileInfoState.collectAsStateWithLifecycle()
    val forRecordsState by viewModel.forRecordsState.collectAsStateWithLifecycle()
    val listOfRecords by viewModel.listOfRecords.collectAsStateWithLifecycle()

    Scaffold() { it ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BackArrowRow(
                onArrowBackClick = backBtnClick,
                title = fileInfoState.title
            )
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
                        text = item,
                        isSelected = item in forRecordsState.listOfSelectedCategories,
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
                val sizeRecords = listOfRecords.size
                items(sizeRecords){ index ->
                    RecordSmartCard(
                        title = listOfRecords[index][forRecordsState.titleColumnIndex],
                        subHeader = listOfRecords[index][forRecordsState.subHeaderColumnIndex],
                        size=270,
                        photo = if(forRecordsState.covImgRecordsIndex!=null) listOfRecords[index][forRecordsState.covImgRecordsIndex!!]
                        else null,
                        onCardClick = {
                            viewModel.fileScreenEvents(FileScreenEvents.OnRecordSelect(index))
                            onRecordClick()
                        }
                    )
                }
            }
        }
    }
}


