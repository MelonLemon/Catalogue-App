package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import android.util.Patterns
import com.melonlemon.catalogueapp.BuildConfig
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Files
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.FileRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TransactionCheckStatus
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.ValidationUrlCheckStatus
import java.net.URL

class CheckUrlValidation(
    private val fileRepository: FileRepository
) {
    suspend operator fun invoke(sheetsId: String, sheetsName: String): ValidationUrlCheckStatus {
        if(sheetsId.isBlank() || sheetsName.isBlank()){
            return ValidationUrlCheckStatus.BlankParameterFailStatus
        }
        val key = BuildConfig.API_KEY
        try {
            val result = fileRepository.getFirstCell(
                spreadsheetId = sheetsId,
                sheetsName = sheetsName,
                apiKey = key
            )
            val error = result.error ?: return ValidationUrlCheckStatus.SuccessStatus

            if(error.code == 403) {
                return ValidationUrlCheckStatus.NoRightsFailStatus
            }
            return ValidationUrlCheckStatus.BrokenUrlFailStatus
        } catch (e: Exception){
            return ValidationUrlCheckStatus.BrokenUrlFailStatus
        }

    }
}