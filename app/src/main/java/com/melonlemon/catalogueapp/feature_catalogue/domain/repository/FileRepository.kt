package com.melonlemon.catalogueapp.feature_catalogue.domain.repository

import kotlinx.coroutines.flow.Flow

interface FileRepository {

    fun getRecords(spreadsheetId:String, range:String, apiKey: String): Flow<List<List<String>>>

    suspend fun getCategories(spreadsheetId:String, columnLetter:String, apiKey: String): List<String>
}