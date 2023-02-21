package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CardInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.RecordInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.ForRecordsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetRecord(
    repository: CatalogueRepository
) {
    suspend operator fun invoke(recordId: Int): RecordInfo {

        return RecordInfo(
            id = -1,
            name = "",
            urlString = "",
            columnsInfo = listOf(),
            tags = listOf()
        )
    }
}