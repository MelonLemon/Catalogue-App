package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Files
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.InfoForLoading
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository

class GetFileById(
    private val repository: CatalogueRepository
) {
    suspend operator fun invoke(fieldId: Int): Files {
        return repository.getFileById(fieldId)
    }

}