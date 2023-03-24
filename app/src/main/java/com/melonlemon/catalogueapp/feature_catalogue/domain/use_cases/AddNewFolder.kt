package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.CheckStatusAddStr

class AddNewFolder(
    private val repository: CatalogueRepository
) {
    suspend operator fun invoke(newName: String, currentFolder: List<CategoryInfo>): CheckStatusAddStr {
        if(newName.isNotBlank()){
            val listOfCategories = currentFolder.map { it.name.lowercase() }
            val isDuplicate = newName.lowercase().trimStart() in listOfCategories
            if(!isDuplicate){
                try {
                    repository.addNewFolder(newName)
                } catch (e: Exception){
                    return CheckStatusAddStr.UnKnownFailStatus
                }
                return CheckStatusAddStr.SuccessStatus
            } else {
                return CheckStatusAddStr.DuplicateFailStatus
            }
        } else {
            return CheckStatusAddStr.BlankFailStatus
        }
    }
}