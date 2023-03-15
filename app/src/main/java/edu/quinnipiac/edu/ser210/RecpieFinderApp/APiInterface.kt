package edu.quinnipiac.edu.ser210.RecpieFinderApp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APiInterface {

    @GET("/v1/recipe")
    @Headers("X-RapidAPI-Key:27c2debd20msh3bb8699f6a24062p1accdfjsn21cb10a1b31c", "X-RapidAPI-Host:recipe-by-api-ninjas.p.rapidapi.com")
    fun getRecipes(@Query("query") query:String) : Call<ArrayList<RecipeItem?>?>?

    companion object {

        var BASE_URL = "https://recipe-by-api-ninjas.p.rapidapi.com"


        fun create() : APiInterface {

            val logging = HttpLoggingInterceptor()
// set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging) // <-- this is the important line!
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .build()
            return retrofit.create(APiInterface::class.java)

        }
    }
}
