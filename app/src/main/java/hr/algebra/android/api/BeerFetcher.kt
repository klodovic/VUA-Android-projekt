package hr.algebra.android.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.algebra.android.AndroidReceiver
import hr.algebra.android.BEER_PROVIDER_URI
import hr.algebra.android.DATA_IMPORTED
import hr.algebra.android.framework.sendBroadcast
import hr.algebra.android.framework.setBooleanPreference
import hr.algebra.android.handler.downloadImageAndStore
import hr.algebra.android.model.Item
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BeerFetcher(private val context: Context)  {
    private var beerApi: BeerApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        beerApi = retrofit.create(BeerApi::class.java)
    }

    fun fetchItems(){
        val request = beerApi.fetchItems()

        request.enqueue(object: Callback<List<Beer>>{
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                response.body()?.let {
                    populateItems(it)
                }
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
            }

        })
    }

    private fun populateItems(beerItems: List<Beer>) {
        GlobalScope.launch {

        beerItems.forEach {

            val imagePath = downloadImageAndStore(
                context,
                it.image_url,
                it.name.replace(" ", "_")
                )

            val values = ContentValues().apply {
                put(Item::name.name, it.name)
                put(Item::tagline.name, it.tagline)
                put(Item::first_brewed.name, it.first_brewed)
                put(Item::description.name, it.description)
                put(Item::image_url.name, imagePath ?:  "")
                put(Item::brewers_tips.name, it.brewers_tips)
                put(Item::contributed_by.name, it.contributed_by)
                put(Item::read.name, false)
            }
            context.contentResolver.insert(BEER_PROVIDER_URI, values)

            }

            context.setBooleanPreference(DATA_IMPORTED, true)
            context.sendBroadcast<AndroidReceiver>()
        }
    }
}