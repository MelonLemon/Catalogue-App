package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.*
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.BackArrowRow
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.MultiTextCard
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.RecordSmartCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    backBtnClick: () -> Unit,
    viewModel: FileViewModel
) {
    val selectedRecordFullInfo by viewModel.selectedRecordFullInfo.collectAsStateWithLifecycle()
    val forRecordsState by viewModel.forRecordsState.collectAsStateWithLifecycle()
    val columnsTitles by viewModel.columnsTitles.collectAsStateWithLifecycle()


    Scaffold(
    ) { it ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Error!!!!
            //java.lang.IndexOutOfBoundsException: Empty list doesn't contain element at index 1
            if(selectedRecordFullInfo.isEmpty()){
                item{
                    BackArrowRow(
                        onArrowBackClick = backBtnClick,
                        title = "Records"
                    )
                }
                item{
                    Text(text="Record is Empty")
                }
            }
            item{
                BackArrowRow(
                    onArrowBackClick = backBtnClick,
                    title = selectedRecordFullInfo[forRecordsState.titleColumnIndex-1]
                )
            }
            item{
                RecordSmartCard(
                    title = selectedRecordFullInfo[forRecordsState.titleColumnIndex-1],
                    subHeader = selectedRecordFullInfo[forRecordsState.categoryColumn-1],
                    size=270,
                    photo = if(forRecordsState.covImgRecordsIndex!=null) selectedRecordFullInfo[forRecordsState.covImgRecordsIndex!!-1]
                    else null,
                    onCardClick = {}
                )
            }
            itemsIndexed(selectedRecordFullInfo){ index, columnInfo ->
                val isDisplayInfo = (index==forRecordsState.titleColumnIndex-1) || (index == forRecordsState.categoryColumn-1)
                val isPhoto = index == forRecordsState.covImgRecordsIndex
                if(!isDisplayInfo && !isPhoto){
                    MultiTextCard(
                        title = columnsTitles[index],
                        text = columnInfo,
                        onValueChange = {}
                    )
                }
            }
        }
    }
}

