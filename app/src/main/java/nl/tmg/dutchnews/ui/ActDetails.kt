package nl.tmg.dutchnews.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.act_details.*
import nl.tmg.core.base.ActParent
import nl.tmg.core.di.DaggerCoreComponent
import nl.tmg.core.di.NetworkModule
import nl.tmg.dutchnews.R
import nl.tmg.dutchnews.databinding.ActDetailsBinding
import nl.tmg.dutchnews.di.AppModule
import nl.tmg.dutchnews.di.DaggerAppComponent
import nl.tmg.dutchnews.dto.Article
import nl.tmg.dutchnews.utils.Apps
import nl.tmg.dutchnews.viewModel.ActDetailsViewModel
import nl.tmg.dutchnews.viewModel.ActDetailsViewModelImpl


class ActDetails : ActParent<ActDetailsViewModel, ActDetailsBinding>() {

    lateinit var article : Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }

        if (intent.hasExtra(detailsIntentKey)){
            article = intent.getParcelableExtra(detailsIntentKey)
            dataBinding.item = article
        }
    }

    override fun getResourceLayoutId(): Int = R.layout.act_details

    override fun getViewModelClass(): Class<ActDetailsViewModel> = ActDetailsViewModel::class.java

    override fun getFactory(): ViewModelProvider.Factory = object : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ActDetailsViewModelImpl() as T
        }
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
}
