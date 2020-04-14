package nl.tmg.dutchnews.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServices {

    @GET("top-headlines")
    fun topHeadLines(
        @Query("country") country   : String,
        @Query("pageSize") pageSize : Int,
        @Query("page") page : Int
    ) : Observable<TopHeadlinesReponse>

}