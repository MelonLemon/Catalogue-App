package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util

sealed class ColumnType(val name: String){
    class TitleColumn(name: String=""): ColumnType(name)
    class SubHeaderColumn(name: String=""): ColumnType(name)
    class CategoryColumn(name: String=""): ColumnType(name)
    class CovImgRecordColumn(name: String=""): ColumnType(name)
}
