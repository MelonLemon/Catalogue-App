package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util

sealed class TypeFile{
    object NewFile: TypeFile()
    object ExistingFile: TypeFile()
}
