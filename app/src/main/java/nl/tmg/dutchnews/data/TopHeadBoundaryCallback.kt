package nl.tmg.dutchnews.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nl.tmg.dutchnews.api.ApiServices
import nl.tmg.dutchnews.api.TopHeadlinesReponse
import nl.tmg.dutchnews.db.LocalCache
import nl.tmg.dutchnews.dto.Article
import nl.tmg.core.dto.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopHeadBoundaryCallback(
    private val pageSize: Int,
    private val country: String,
    private val services: ApiServices,
    private val localCache: LocalCache
) : PagedList.BoundaryCallback<Article>() {

    private var lastRequestedPage = 1

    val networkState = MutableLiveData<NetworkState>()

    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        getTopHeadArticle(country = country)
    }

    /**
     * Called when the item at the end of the PagedList has been loaded, and access has
     * occurred within [Config.prefetchDistance] of it.
     *
     *
     * No more data will be appended to the PagedList after this item.
     *
     * @param itemAtEnd The first item of PagedList
     */
    override fun onItemAtEndLoaded(itemAtEnd: Article) {
        super.onItemAtEndLoaded(itemAtEnd)
        getTopHeadArticle(country = country)
    }

    private fun getTopHeadArticle(country: String) {
        if (isRequestInProgress) return

        networkState.postValue(NetworkState.LOADING)

        isRequestInProgress = true

        val disposable = services.topHeadLines(
            country = country,
            pageSize = pageSize,
            page = lastRequestedPage
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe({
                localCache.insertArticle(it.articles) {
                    lastRequestedPage++
                    isRequestInProgress = false
                    networkState.postValue(NetworkState.LOADED)
                }
            }, {
                networkState.postValue(NetworkState.error(it.message))
                isRequestInProgress = false
            })
    }
}