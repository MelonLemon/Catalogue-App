package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.presentation.home.FoldersInfoState

class GetFolders(
    private val repository: CatalogueRepository
) {
    suspend operator fun invoke(): List<CategoryInfo>{
        return repository.getFolders()
    }
}