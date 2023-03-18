package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.presentation.home.FoldersInfoState

class GetFolders(
    private val repository: CatalogueRepository
) {
    suspend operator fun invoke(): List<CategoryInfo>{
        val folders = repository.getFolders()
        if(folders.isNotEmpty()){
            val categoryInfo = mutableListOf<CategoryInfo>()
            folders.forEach { categoryInfo.add(CategoryInfo(it.id!!, it.name)) }
            return categoryInfo
        }
        return emptyList()

    }
}