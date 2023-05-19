package com.example.restapiidemo.network

import com.example.restapiidemo.home.data.FoodModel
import retrofit2.Call
import retrofit2.http.*

class Empty {}

interface ApiInterface {
    @GET("foods")
    fun fetchAllFoods(): Call<List<FoodModel>>

    @POST("foods")
    fun createFood(@Body foodModel: FoodModel):Call<Empty>

    @DELETE("foods/{id}")
    fun deleteFood(@Path("id") id:String):Call<Empty>
}