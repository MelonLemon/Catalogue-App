package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

data class CatalogueUseCases(
    val getFolders: GetFolders,
    val addNewFolder: AddNewFolder,
    val getFiles: GetFiles,
    val getFilteredList: GetFilteredList,
    val getRecords: GetRecords,
    val getFileColumnTitles: GetFileColumnTitles,
    val addNewFile: AddNewFile,
    val getFileCategories: GetFileCategories,
    val getInfoForLoading: GetInfoForLoading,
    val getFilteredFiles: GetFilteredFiles,
    val deleteFiles: DeleteFiles,
    val deleteFolder: DeleteFolder,
    val getFirstRow: GetFirstRow,
    val getFileById: GetFileById
)
