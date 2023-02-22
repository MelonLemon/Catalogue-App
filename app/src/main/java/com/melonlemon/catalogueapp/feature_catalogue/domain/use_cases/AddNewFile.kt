package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.FileInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository

class AddNewFile(
    private val repository: CatalogueRepository
) {
    suspend operator fun invoke(fileInfo: FileInfo){
        repository.addNewFile(fileInfo)
    }
}