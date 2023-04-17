package com.melonlemon.catalogueapp.feature_catalogue.data.data_source.api

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.FileObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleSheetApiService {

    @GET("spreadsheets/{spreadsheetId}/values/{range}?majorDimension=ROWS&")
    suspend fun getAllRecords(
        @Path("spreadsheetId") spreadsheetId:String,
        @Path("range") range:String,
        @Query("key") apiKey: String): FileObject


    @GET("spreadsheets/{spreadsheetId}/values/'{sheetsName}'!A1:A1?majorDimension=ROWS&")
    suspend fun getFirstCell(
        @Path("spreadsheetId") spreadsheetId:String,
        @Path("sheetsName") sheetsName:String,
        @Query("key") apiKey: String): FileObject

    @GET("spreadsheets/{spreadsheetId}/values/{columnLetter}:{columnLetter}?majorDimension=COLUMNS&")
    suspend fun getColumn(
        @Path("spreadsheetId") spreadsheetId:String,
        @Path("columnLetter") columnLetter:String,
        @Query("key") apiKey: String): FileObject

}