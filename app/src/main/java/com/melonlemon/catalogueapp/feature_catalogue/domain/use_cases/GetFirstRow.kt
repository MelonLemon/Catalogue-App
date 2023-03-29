package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.BuildConfig
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.FileRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.FileRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TransactionCheckStatus
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.ForRecordsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first

class GetFirstRow(
    private val fileRepository: FileRepository
) {

    suspend operator fun invoke(sheetsId: String, sheetsName: String, number: Int): Pair<TransactionCheckStatus, List<String>?>{
        if(sheetsId.isBlank()){
            return Pair(TransactionCheckStatus.BlankParameterFailStatus, null)
        }
        if(number==0){
           return Pair(TransactionCheckStatus.UnKnownFailStatus, null)
        }
        val key = BuildConfig.API_KEY
        val sheetName = "'$sheetsName'"

        val endRange = '@' + number
        return try {
            val fileObject = fileRepository.getRecords(
                spreadsheetId = sheetsId,
                range = "$sheetName!A1:${endRange}1",
                apiKey = key
            ).first()
            if(fileObject.error==null && fileObject.values!=null){
                Pair(TransactionCheckStatus.SuccessStatus, fileObject.values[0])
            } else {
                Pair(TransactionCheckStatus.UnKnownFailStatus, null)
            }
        } catch (e: Exception){
            Pair(TransactionCheckStatus.UnKnownFailStatus, null)
        }



}
}