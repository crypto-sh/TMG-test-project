package nl.tmg.dutchnews.di

import dagger.Component
import nl.tmg.core.di.CoreComponent
import nl.tmg.dutchnews.ui.ActDetails
import nl.tmg.dutchnews.ui.ActMain


@Component(
    modules = arrayOf(
        AppModule::class
    ),
    dependencies = arrayOf(
        CoreComponent::class
    )
)
@AppScope
interface AppComponent {
    fun inject(target : ActMain)
    fun inject(target : ActDetails)
}