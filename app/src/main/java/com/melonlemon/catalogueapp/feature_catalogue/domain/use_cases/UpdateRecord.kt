package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.RecordInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository

class UpdateRecord(
    private val repository: CatalogueRepository
) {
    suspend operator fun invoke(fieldId: Int, recordInfo: RecordInfo){
        repository.updateRecord(fieldId, recordInfo)
    }
}