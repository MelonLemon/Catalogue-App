package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CardInfo

class GetFilteredList(
) {
     operator fun invoke(listCards:List<CardInfo>, searchText: String): List<CardInfo> {
        if(searchText.isNotBlank()){
            return listCards.filter { file ->
                file.tags.any { tag ->
                    tag.contains(searchText)
                } || file.title.contains(searchText)
            }
        }  else {
            return listCards
        }
}
}