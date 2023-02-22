package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.CatalogueRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.*
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.BackArrowRow
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.HalfSmartCard
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.MultiTextCard
import com.melonlemon.catalogueapp.feature_catalogue.presentation.record.RecordScreenEvents
import com.melonlemon.catalogueapp.feature_catalogue.presentation.record.RecordViewModel
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    viewModel: RecordViewModel
) {
    val selectedRecordFullInfo by viewModel.selectedRecordFullInfo.collectAsStateWithLifecycle()
    val saveRecordChanging by viewModel.saveRecordChanging.collectAsStateWithLifecycle()

    LaunchedEffect(saveRecordChanging){
        //navigation
    }

    Scaffold(
    ) { it ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item{
                BackArrowRow(
                    onArrowBackClick = {
                        viewModel.recordScreenEvents(RecordScreenEvents.SaveRecordChanges)
                    },
                    title = stringResource(R.string.folders)
                )
            }
            item{
                HalfSmartCard(
                    photo= null,
                    tags = selectedRecordFullInfo.tags ?: listOf(stringResource(R.string.no_tags)),
                    size=270,
                )
            }
            itemsIndexed(selectedRecordFullInfo.columnsInfo){ index, columnInfo ->
                MultiTextCard(
                    title = columnInfo.label,
                    text = columnInfo.text,
                    onValueChange = { text ->
                        viewModel.recordScreenEvents(
                            RecordScreenEvents.OnSelRecordColumnChange(
                            index = index,
                            text = text
                        ))
                    }
                )

            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordScreenPreview() {
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
        val viewModel = RecordViewModel(useCases, SavedStateHandle())
        RecordScreen(viewModel)
    }
}