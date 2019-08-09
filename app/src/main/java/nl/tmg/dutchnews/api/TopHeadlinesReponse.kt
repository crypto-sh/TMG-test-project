package nl.tmg.dutchnews.api


import nl.tmg.dutchnews.dto.Article


data class TopHeadlinesReponse(
    val status: String,
    val totalResults: String,
    val code: String,
    val message: String,
    val articles: List<Article>
)
