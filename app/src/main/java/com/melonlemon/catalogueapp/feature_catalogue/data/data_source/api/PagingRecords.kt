package com.melonlemon.catalogueapp.feature_catalogue.data.data_source.api


import androidx.paging.PagingSource
import androidx.paging.PagingState

class PagingRecords(
    private val spreadsheetId: String,
    private val sheetsName: String,
    private val columnLetter: String,
    private val apiKey: String,
    private val googleSheetApiService: GoogleSheetApiService
): PagingSource<Int, List<String>>() {

    override fun getRefreshKey(state: PagingState<Int, List<String>>): Int? {
       return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, List<String>> {
        return try {
            val position = params.key ?: 1
            val limit = 20
            val response = googleSheetApiService.getPagingAllRecords(
                spreadsheetId = spreadsheetId,
                sheetsName = sheetsName,
                start = position*limit,
                end = position*limit + limit,
                columnLetter = columnLetter,
                apiKey = apiKey
            )

            val value = response.values
            val nextKey = if(value.isNullOrEmpty()){
                null
            } else {
                position + (params.loadSize/limit)
            }
            if(value != null ){
                LoadResult.Page(
                    data = value,
                    prevKey = if(position==1) null else position - 1,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(
                    throwable = IllegalArgumentException()
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}