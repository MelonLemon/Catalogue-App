package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CardInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.ForRecordsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetRecords(
    private val repository: CatalogueRepository
) {
    operator fun invoke(forRecordsState: ForRecordsState): Flow<List<CardInfo>> {
        if(forRecordsState.isAllTagsSelected){
            return repository.getAllRecords(forRecordsState.fileId)
        } else {
            return repository.getRecordsByTags(
                fileId = forRecordsState.fileId,
                tags = forRecordsState.listOfSelectedTags
            )
        }

    }
}