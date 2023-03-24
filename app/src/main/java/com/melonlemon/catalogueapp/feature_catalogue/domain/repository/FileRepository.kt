package com.melonlemon.catalogueapp.feature_catalogue.domain.repository

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.RecordObject
import kotlinx.coroutines.flow.Flow

interface FileRepository {

    fun getRecords(spreadsheetId:String, range:String, apiKey: String): Flow<RecordObject>

    suspend fun getColumn(spreadsheetId:String, columnLetter:String, apiKey: String): RecordObject
}