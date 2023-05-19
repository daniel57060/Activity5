package com.example.restapiidemo.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restapiidemo.home.data.HomeRepository
import com.example.restapiidemo.home.data.FoodModel

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private var homeRepository: HomeRepository? = null
    var foodModelListLiveData: LiveData<List<FoodModel>>? = null
    var createPostLiveData: LiveData<FoodModel>? = null
    var deletePostLiveData: LiveData<Boolean>? = null

    init {
        homeRepository = HomeRepository()
        foodModelListLiveData = MutableLiveData()
        createPostLiveData = MutableLiveData()
        deletePostLiveData = MutableLiveData()
    }

    fun fetchAllFoods() {
        foodModelListLiveData = homeRepository?.fetchAllPosts()
    }

    fun createFood(foodModel: FoodModel) {
        createPostLiveData = homeRepository?.createFood(foodModel)
    }

    fun deleteFood(id: String) {
        deletePostLiveData = homeRepository?.deleteFood(id)
    }
}
