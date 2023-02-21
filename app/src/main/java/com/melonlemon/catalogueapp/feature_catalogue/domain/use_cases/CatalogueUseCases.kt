package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

data class CatalogueUseCases(
    val getFolders: GetFolders,
    val addNewFolder: AddNewFolder,
    val getFiles: GetFiles,
    val getFilteredList: GetFilteredList,
    val getRecords: GetRecords,
    val getFileInfo: GetFileInfo,
    val updateRecord: UpdateRecord,
    val getRecord: GetRecord,
    val addNewFile: AddNewFile,
    val getTagsRecords: GetTagsRecords,
    val getFileColumns: GetFileColumns,
    val addTagRecord: AddTagRecord,
    val addNewRecord: AddNewRecord
)
