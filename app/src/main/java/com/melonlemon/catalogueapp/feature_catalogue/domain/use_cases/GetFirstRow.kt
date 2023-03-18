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

    suspend operator fun invoke(sheetsId: String, number: Int): Pair<TransactionCheckStatus, List<String>?>{
        if(sheetsId.isBlank()){
            return Pair(TransactionCheckStatus.BlankParameterFailStatus, null)
        }
        val key = BuildConfig.API_KEY
        if(number==0){
            return try {
                //change to name of sheet
                val result = fileRepository.getRecords(
                    spreadsheetId = sheetsId,
                    range = "A1:A1",
                    apiKey = key
                ).first()
                Pair(TransactionCheckStatus.SuccessStatus, result[0])
            } catch (e: Exception){
                Pair(TransactionCheckStatus.UnKnownFailStatus, null)
            }
        } else {
            val endRange = '@' + number
            return try {
                val result = fileRepository.getRecords(
                    spreadsheetId = sheetsId,
                    range = "A1:${endRange}1",
                    apiKey = key
                ).first()
                Pair(TransactionCheckStatus.SuccessStatus, result[0])
            } catch (e: Exception){
                Pair(TransactionCheckStatus.UnKnownFailStatus, null)
            }
        }


}
}