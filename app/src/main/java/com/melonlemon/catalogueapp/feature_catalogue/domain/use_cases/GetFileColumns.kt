package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.ColumnInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.SelectedCategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository

class GetFileColumns(
    private val repository: CatalogueRepository
) {
    suspend operator fun invoke(fileId: Int): List<ColumnInfo> {

        val columns = repository.getFileColumns(fileId)
        var id = 1
        val listOfColumns = mutableListOf<ColumnInfo>()
        columns.forEach {
            listOfColumns.add(ColumnInfo(id = id, label = it, text =""))
            id +=1
        }
        return listOfColumns
    }
}