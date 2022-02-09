package hr.algebra.android.api

import retrofit2.Call
import retrofit2.http.GET

const val API_URL = "https://api.punkapi.com/"
interface BeerApi {
    @GET("v2/beers")
    fun fetchItems() : Call<List<Beer>>
}