package com.reggya.newsapp.core.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.reggya.newsapp.BuildConfig
import com.reggya.newsapp.core.model.ArticlesItem
import com.reggya.newsapp.core.network.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsPagingSource @Inject constructor(private val apiService: ApiService): PagingSource<Int, ArticlesItem>(){
    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        val anchorPage = state.anchorPosition?.let {
            state.closestPageToPosition(it)
        }
        return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        val pageIndex = params.key ?: 1
        return try {
            val client = apiService.getHeadlineNews("bbc-news", pageIndex, BuildConfig.API_KEY)
            val response = client.articles
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isEmpty()) null else pageIndex + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}