package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.RecordInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.SelectedCategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository

class GetTagsRecords(
    private val repository: CatalogueRepository
) {
    suspend operator fun invoke(fileId: Int): List<String> {

        return  repository.getFileRecordTags(fileId)
    }
}