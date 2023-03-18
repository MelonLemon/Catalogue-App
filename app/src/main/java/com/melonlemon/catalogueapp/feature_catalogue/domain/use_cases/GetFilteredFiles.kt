package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CardInfo

class GetFilteredFiles (
) {
    operator fun invoke(listCards:List<CardInfo>, searchText: String): List<CardInfo> {
        //delete spaces in tags/title and search text
        if(searchText.isNotBlank()){
            return listCards.filter { file ->
                file.tags.any { tag ->
                    tag.contains(searchText, ignoreCase = true)
                } || file.title.contains(searchText, ignoreCase = true)
            }
        }  else {
            return listCards
        }
    }
}