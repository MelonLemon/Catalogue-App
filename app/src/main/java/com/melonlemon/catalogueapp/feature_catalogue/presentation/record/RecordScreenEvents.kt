package com.melonlemon.catalogueapp.feature_catalogue.presentation.record

sealed class RecordScreenEvents{
    data class OnSelRecordColumnChange(val index: Int, val text: String): RecordScreenEvents()
    object SaveRecordChanges: RecordScreenEvents()
}
