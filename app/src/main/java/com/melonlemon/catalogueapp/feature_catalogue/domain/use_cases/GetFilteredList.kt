package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CardInfo

class GetFilteredList(
) {
     operator fun invoke(
         listCards:List<List<String>>,
         searchText: String,
         titleIndex: Int,
         categoryIndex: Int
     ): List<List<String>> {


         if(searchText.isNotBlank() && listCards.isNotEmpty()){
             return listCards.filter { file ->
                 val title = file[titleIndex].trim()
                 val category = file[categoryIndex].trim()
                 title.contains(searchText.trim(), ignoreCase = true)
                         || category.contains(searchText.trim(), ignoreCase = true)
             }
         }  else {
             return listCards
         }

}
}