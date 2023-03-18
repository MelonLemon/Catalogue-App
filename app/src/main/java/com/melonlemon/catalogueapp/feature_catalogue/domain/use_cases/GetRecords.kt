package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.BuildConfig
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.FileRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.FileRepository
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.ForRecordsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class GetRecords(
    private val fileRepository: FileRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(forRecordsState: ForRecordsState): Flow<List<List<String>>> {
        if(forRecordsState.fileId!=-1){
            val endRange = '@' + forRecordsState.numberOfColumns
            val key = BuildConfig.API_KEY
            if(forRecordsState.isAllCategoriesSelected){
                return fileRepository.getRecords(
                    spreadsheetId = forRecordsState.sheetId,
                    range = "A2:$endRange",
                    apiKey = key
                )
            } else {
                return fileRepository.getRecords(
                    spreadsheetId = forRecordsState.sheetId,
                    range = "A:$endRange",
                    apiKey = key
                ).mapLatest { list ->
                    list.filter { it[forRecordsState.categoryColumn] in forRecordsState.listOfSelectedCategories}
                }
            }
        } else {
            return emptyFlow()
        }




}
}