package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CardInfo

class GetFilteredList(
) {
     operator fun invoke(listCards:List<List<String>>, searchText: String): List<List<String>> {
         //delete spaces in tags/title and search text
        if(searchText.isNotBlank()){
            return listCards.filter { file ->
                file.contains(searchText)
            }
        }  else {
            return listCards
        }
}
}