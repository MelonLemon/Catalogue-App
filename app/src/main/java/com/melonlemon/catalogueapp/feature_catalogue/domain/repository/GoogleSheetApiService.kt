package com.melonlemon.catalogueapp.feature_catalogue.domain.repository

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.RecordObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleSheetApiService {

    @GET("spreadsheets/{spreadsheetId}/values/{range}?majorDimension=ROWS&")
    suspend fun getAllRecords(
        @Path("spreadsheetId") spreadsheetId:String,
        @Path("range") range:String,
        @Query("key") apiKey: String): RecordObject

    @GET("spreadsheets/{spreadsheetId}/values/{columnLetter}:{columnLetter}?majorDimension=COLUMNS&")
    suspend fun getColumn(
        @Path("spreadsheetId") spreadsheetId:String,
        @Path("columnLetter") columnLetter:String,
        @Query("key") apiKey: String): RecordObject

}