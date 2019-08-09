package nl.tmg.dutchnews.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import nl.tmg.core.dto.NetworkState


data class ArticleResult(
    val networkStatus: MutableLiveData<NetworkState>,
    val data: LiveData<PagedList<Article>>
)