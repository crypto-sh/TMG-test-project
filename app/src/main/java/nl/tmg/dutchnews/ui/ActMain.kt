package nl.tmg.dutchnews.ui


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.act_main.*
import nl.tmg.core.base.ActParent
import nl.tmg.core.di.DaggerCoreComponent
import nl.tmg.core.di.NetworkModule
import nl.tmg.dutchnews.R
import nl.tmg.dutchnews.adapters.RvAdapterArticle
import nl.tmg.dutchnews.data.ArticleRepository
import nl.tmg.dutchnews.databinding.ActMainBinding

import nl.tmg.dutchnews.di.AppModule
import nl.tmg.dutchnews.di.DaggerAppComponent
import nl.tmg.dutchnews.dto.Article
import nl.tmg.dutchnews.utils.Apps
import nl.tmg.dutchnews.viewModel.ActMainViewModel
import nl.tmg.dutchnews.viewModel.ActMainViewModelImpl
import javax.inject.Inject


class ActMain : ActParent<ActMainViewModel, ActMainBinding>() {

    @Inject
    lateinit var repository: ArticleRepository

    val adapter: RvAdapterArticle by lazy {
        RvAdapterArticle {
            showDetails(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val countries: Array<out String> = resources.getStringArray(R.array.countries_array)
        ArrayAdapter(this, android.R.layout.simple_list_item_1, countries).also { adapter ->
            etCountry.setAdapter(adapter)
        }
        etCountry.setOnItemClickListener { parent, _, position, _ ->
            viewModel.getCountryTopHeadNews(position)
            hideKeyboard(etCountry)
        }
        viewModel.getDataArticle().observe(this, Observer {
            adapter.submitList(it)
        })
        dataBinding.adapter = adapter
    }

    override fun inject() {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .coreComponent(
                DaggerCoreComponent.builder()
                    .networkModule(NetworkModule((application as Apps).apiKey()))
                    .build()
            ).build()
            .inject(this)
    }

    override fun getResourceLayoutId(): Int = R.layout.act_main

    override fun getViewModelClass(): Class<ActMainViewModel> = ActMainViewModel::class.java

    override fun getFactory(): ViewModelProvider.Factory = object : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            val twoLetters  : Array<out String> = resources.getStringArray(R.array.country_2letter)
            return ActMainViewModelImpl(twoLetters,repository) as T
        }
    }

    override fun showProgress() {
        super.showProgress()
        dataBinding.showingProgress = true
    }

    override fun hideProgress() {
        super.hideProgress()
        dataBinding.showingProgress = false
    }

    override fun showError(error: String?) {
        super.showError(error)
        prg.visibility = View.INVISIBLE
        tvError.text = error
    }

    private fun showDetails(item: Article) {
        val details = Intent(this, ActDetails::class.java)
        details.putExtra(detailsIntentKey, item)
        startActivity(details)
    }
}
