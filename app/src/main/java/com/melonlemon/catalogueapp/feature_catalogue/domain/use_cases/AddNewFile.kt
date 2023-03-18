package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.FileInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Files
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository

class AddNewFile(
    private val repository: CatalogueRepository
) {
    suspend operator fun invoke(file: Files){
        repository.addNewFile(file)
    }
}