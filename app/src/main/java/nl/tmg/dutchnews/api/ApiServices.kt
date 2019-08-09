package nl.tmg.dutchnews.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServices {

    @GET("top-headlines")
    fun topHeadLines(
        @Query("country") country   : String,
        @Query("pageSize") pageSize : Int,
        @Query("page") page : Int
    ) : Call<TopHeadlinesReponse>

}