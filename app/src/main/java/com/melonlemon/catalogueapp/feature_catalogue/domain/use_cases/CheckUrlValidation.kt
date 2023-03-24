package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import android.util.Patterns
import com.melonlemon.catalogueapp.BuildConfig
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Files
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TransactionCheckStatus

class CheckUrlValidation(
    private val baseUrl: String
) {
    suspend operator fun invoke(sheetsId: String, sheetsName: String): TransactionCheckStatus {
        val key = BuildConfig.API_KEY
        val urlString = baseUrl + "spreadsheets/$sheetsId/values/$sheetsName!A1?majorDimension=ROWS&key=$key"
        val result = Patterns.WEB_URL.matcher(urlString).matches()
        return if(result) TransactionCheckStatus.SuccessStatus else TransactionCheckStatus.UnKnownFailStatus
    }
}