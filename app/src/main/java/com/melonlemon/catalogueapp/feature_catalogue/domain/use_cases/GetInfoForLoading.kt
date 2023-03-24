package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CardInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.InfoForLoading
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.ForRecordsState
import kotlinx.coroutines.flow.Flow

class GetInfoForLoading(
    private val repository: CatalogueRepository
) {
    suspend operator fun invoke(fieldId: Int): InfoForLoading {
        val file = repository.getFileById(fieldId)

        return InfoForLoading(
            sheetsId = file.sheetsId,
            sheetsName = file.sheetsName,
            titleColumnIndex = file.titleColumnIndex,
            subHeaderColumnIndex = file.subHeaderColumnIndex,
            categoryColumnIndex = file.categoryColumnIndex,
            covImgRecordsIndex = file.covImgRecColumnIndex,
            numberOfColumns = file.numColumns
        )
    }

}