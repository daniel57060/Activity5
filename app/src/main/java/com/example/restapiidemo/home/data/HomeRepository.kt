package com.example.restapiidemo.home.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restapiidemo.network.ApiClient
import com.example.restapiidemo.network.ApiInterface
import com.example.restapiidemo.network.Empty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

    private var apiInterface: ApiInterface? = null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }

    fun fetchAllPosts(): LiveData<List<FoodModel>> {
        val data = MutableLiveData<List<FoodModel>>()

        apiInterface?.fetchAllFoods()?.enqueue(object : Callback<List<FoodModel>> {
            override fun onFailure(call: Call<List<FoodModel>>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<List<FoodModel>>,
                response: Response<List<FoodModel>>
            ) {
                val res = response.body()
                if (response.code() == 200 && res != null) {
                    data.value = res
                } else {
                    data.value = null
                }
            }
        })

        return data

    }

    fun createFood(foodModel: FoodModel): LiveData<FoodModel> {
        val data = MutableLiveData<FoodModel>()

        apiInterface?.createFood(foodModel)?.enqueue(object : Callback<Empty> {
            override fun onFailure(call: Call<Empty>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<Empty>, response: Response<Empty>) {
                val body = response.body()
                if (response.code() == 201) {
                    data.value = foodModel
                } else {
                    data.value = null
                }
            }
        })

        return data
    }

    fun deleteFood(id: String): LiveData<Boolean> {
        val data = MutableLiveData<Boolean>()

        apiInterface?.deleteFood(id)?.enqueue(object : Callback<Empty> {
            override fun onFailure(call: Call<Empty>, t: Throwable) {
                data.value = false
            }

            override fun onResponse(call: Call<Empty>, response: Response<Empty>) {
                data.value = response.code() == 200
            }
        })

        return data
    }
}