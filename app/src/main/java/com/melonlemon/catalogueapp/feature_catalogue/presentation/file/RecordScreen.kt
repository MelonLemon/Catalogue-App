package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.CatalogueRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.AddNewFolder
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.GetHomeScreenState
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.BackArrowRow
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.HalfSmartCard
import com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components.MultiTextCard
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    viewModel: FileViewModel
) {
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
                        // Navigation
                    },
                    title = stringResource(R.string.folders)
                )
            }
            item{
                HalfSmartCard(
                    photo= painterResource(id = R.drawable.ic_launcher_background), // change
                    tags = viewModel.fileScreenState.value.selectedRecordFullInfo?.tags ?:
                    listOf(stringResource(R.string.no_tags)),
                    size=270,
                )
            }
            if(viewModel.fileScreenState.value.selectedRecordFullInfo != null){

                items(viewModel.fileScreenState.value
                    .selectedRecordFullInfo!!.columnsInfo){ columnInfo ->
                    MultiTextCard(
                        title = columnInfo.label,
                        text = columnInfo.text,
                        onValueChange = { text ->
                            viewModel.onSelRecordColumnChange(
                                id = columnInfo.id,
                                text = columnInfo.text
                            )
                        }
                    )

                }
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
            getHomeScreenState = GetHomeScreenState(repository),
            addNewFolder = AddNewFolder(repository)
        )
        val viewModel = FileViewModel(useCases)
        RecordScreen(viewModel)
    }
}