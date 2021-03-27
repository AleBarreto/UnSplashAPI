package com.barreto.unsplashapi.api.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.barreto.unsplashapi.api.UnSplashApi
import com.barreto.unsplashapi.model.UnSplashPhoto

import retrofit2.HttpException
import java.io.IOException

private const val UN_SPLASH_STARTING_PAGE_INDEX = 1

class UnSplashPagingSource(
    private val unSplashApi: UnSplashApi,
    private val query: String
) : PagingSource<Int, UnSplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnSplashPhoto> {
        val position = params.key ?: UN_SPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unSplashApi.searchPhotos(query, position, params.loadSize)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (position == UN_SPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnSplashPhoto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}