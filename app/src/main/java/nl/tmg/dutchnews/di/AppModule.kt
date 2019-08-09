package nl.tmg.dutchnews.di

import android.content.Context
import dagger.Module
import dagger.Provides
import nl.tmg.dutchnews.api.ApiServices
import nl.tmg.dutchnews.data.ArticleRepository
import nl.tmg.dutchnews.data.ArticleRepositoryImpl
import nl.tmg.dutchnews.db.LocalCache
import nl.tmg.dutchnews.db.LocalCacheImpl
import nl.tmg.dutchnews.db.TMGDataBase
import nl.tmg.core.utils.AppExecutor
import retrofit2.Retrofit


@Module
class AppModule(private val context: Context) {

    @Provides
    @AppScope
    fun provideExecutore() : AppExecutor {
        return AppExecutor()
    }

    @Provides
    @AppScope
    fun provideApiServices(api : Retrofit) : ApiServices {
        return api.create(ApiServices::class.java)
    }

    @Provides
    @AppScope
    fun provideDataBase() : TMGDataBase {
        return TMGDataBase.getInstance(context,false)
    }

    @Provides
    @AppScope
    fun provideLocalCache(database : TMGDataBase, executor: AppExecutor) : LocalCache{
        return LocalCacheImpl(articleDao = database.getArticleDao(),executor = executor.ioDisk)
    }

    @Provides
    @AppScope
    fun provideArticleRepository(services: ApiServices, db : LocalCache) : ArticleRepository {
        return ArticleRepositoryImpl(services = services,localCache = db)
    }

}