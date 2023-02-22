package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.CheckStatusAddStr

class AddNewFolder(
    private val repository: CatalogueRepository
) {
    suspend operator fun invoke(name: String, currentFolder: List<CategoryInfo>): CheckStatusAddStr {
        if(name.isNotBlank()){
            val isDuplicate = name in currentFolder.map{ it.name }
            if(!isDuplicate){
                try {
                    repository.addNewFolder(name)
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