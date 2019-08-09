package nl.tmg.dutchnews.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import nl.tmg.core.base.BaseViewModel
import nl.tmg.dutchnews.data.ArticleRepository
import nl.tmg.dutchnews.dto.Article
import nl.tmg.core.dto.NetworkState


abstract class ActMainViewModel  : BaseViewModel() {
    abstract fun getCountryTopHeadNews(position : Int)
    abstract fun getDataArticle()   : LiveData<PagedList<Article>>
}


class ActMainViewModelImpl(
    private val countryLetter : Array<out String>,
    private val repository: ArticleRepository
) : ActMainViewModel() {

    private val country = MutableLiveData<String>()

    private val repo = Transformations.map(country) {
        repository.getTopHeadLines(it)
    }

    override fun getDataArticle(): LiveData<PagedList<Article>> = Transformations.switchMap(repo){
        it.data
    }

    override fun getNetworkStatus(): LiveData<NetworkState>  =  Transformations.switchMap(repo){
        it.networkStatus
    }

    override fun getCountryTopHeadNews(position: Int) {
        this.country.postValue(countryLetter[position])
    }
}