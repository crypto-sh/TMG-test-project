package nl.tmg.core.di

import dagger.Component
import retrofit2.Retrofit


@Component(modules = arrayOf(NetworkModule::class))
interface CoreComponent {
    fun getRetrofit() : Retrofit
}