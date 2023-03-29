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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.*
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.BackArrowRow
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.ErrorMsgView
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

            if(selectedRecordFullInfo.isEmpty()){
                item{
                    BackArrowRow(
                        onArrowBackClick = backBtnClick,
                        title = stringResource(R.string.record)
                    )
                }
                item{
                    ErrorMsgView(
                        text = stringResource(R.string.record_empty),
                        imageBitmap = ImageBitmap.imageResource(id = R.drawable.goodies_warning)
                    )
                }
            }
            item{
                BackArrowRow(
                    onArrowBackClick = backBtnClick,
                    title = selectedRecordFullInfo[forRecordsState.titleColumnIndex]
                )
            }
            item{
                RecordSmartCard(
                    title = selectedRecordFullInfo[forRecordsState.titleColumnIndex],
                    subHeader = selectedRecordFullInfo[forRecordsState.categoryColumn],
                    size=270,
                    photo = if(forRecordsState.covImgRecordsIndex!=null) selectedRecordFullInfo[forRecordsState.covImgRecordsIndex!!]
                    else null,
                    onCardClick = {}
                )
            }
            itemsIndexed(selectedRecordFullInfo){ index, columnInfo ->
                val isDisplayInfo = (index==forRecordsState.titleColumnIndex) || (index == forRecordsState.categoryColumn)
                val isPhoto = index == forRecordsState.covImgRecordsIndex
                if(!isDisplayInfo && !isPhoto){
                    MultiTextCard(
                        modifier = Modifier.fillMaxWidth(),
                        title = columnsTitles[index].ifBlank { stringResource(R.string.no_title) },
                        text = columnInfo.ifBlank { stringResource(R.string.no_text) },
                        onValueChange = {}
                    )
                }
            }
        }
    }
}

