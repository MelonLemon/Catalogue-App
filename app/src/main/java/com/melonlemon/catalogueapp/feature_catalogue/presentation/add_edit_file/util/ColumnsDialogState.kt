package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util

data class ColumnsDialogState(
    val selectedIndex: Int = 0,
    val columnType: ColumnType = ColumnType.TitleColumn()
)
