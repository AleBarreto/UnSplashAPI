package com.barreto.unsplashapi.api.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.barreto.unsplashapi.api.UnSplashApi
import javax.inject.Inject


class UnSplashRepository @Inject constructor(private val unSplashApi: UnSplashApi) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnSplashPagingSource(unSplashApi, query) }
        ).liveData

}