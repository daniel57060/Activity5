package com.example.restapiidemo.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val BASEURL = "https://sm-activity-57060.free.beeceptor.com/"
class ApiClient {
    companion object{

        private var retrofit:Retrofit?=null

        fun getApiClient(): Retrofit {
            if (retrofit == null) {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                val okHttpClient = OkHttpClient.Builder()
                    .readTimeout(100, TimeUnit.SECONDS)
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .build()
                retrofit = Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit!!
        }
    }
}