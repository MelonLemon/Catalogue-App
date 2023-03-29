package com.melonlemon.catalogueapp.feature_catalogue.domain.model

import com.google.gson.annotations.SerializedName

data class FileObject(
    @SerializedName("range") val range: String? = null,
    @SerializedName("majorDimension") val majorDimension: String?  = null,
    @SerializedName("values") val values: List<List<String>>?  = null,
    @SerializedName("error") val error: NetworkError?  = null
)



data class NetworkError(
    val code: Int,
    val message: String,
    val status: String
)

