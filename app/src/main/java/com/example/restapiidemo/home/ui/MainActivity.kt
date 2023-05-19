package com.example.restapiidemo.home.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.FoodModel
import com.example.restapiidemo.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_post_dialog.view.*

class MainActivity : AppCompatActivity(), HomeAdapter.HomeListener {

    private lateinit var vm: HomeViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProvider(this)[HomeViewModel::class.java]

        initAdapter()

        vm.fetchAllFoods()

        vm.foodModelListLiveData?.observe(this, Observer {
            if (it != null) {
                rv_home.visibility = View.VISIBLE
                adapter.setData(it as ArrayList<FoodModel>)
            } else {
                showToast("Something went wrong")
            }
            progress_home.visibility = View.GONE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_create_post -> showCreateFoodDialog()
        }
        return true
    }

    private fun showCreateFoodDialog() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.create_post_dialog, null)
        dialog.setContentView(view)

        view.btn_submit.setOnClickListener {
            val name = view.nameInput.text.toString().trim()

            if (title.isNotEmpty()) {
                val foodModel = FoodModel("1", name)
                vm.createFood(foodModel)
                vm.createPostLiveData?.observe(this, Observer {
                    if (it != null) {
                        adapter.addData(it)
                        rv_home.smoothScrollToPosition(0)
                    } else {
                        showToast("Cannot create post at the moment")
                    }
                    dialog.dismiss()
                })
            } else {
                showToast("Please fill data carefully!")
            }
        }

        dialog.show()

        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun initAdapter() {
        adapter = HomeAdapter(this)
        rv_home.layoutManager = LinearLayoutManager(this)
        rv_home.adapter = adapter
    }

    override fun onItemDeleted(foodModel: FoodModel, position: Int) {
        foodModel.id.also { vm.deleteFood(it) }
        vm.deletePostLiveData?.observe(this, Observer {
            if (it != null) {
                adapter.removeData(position)
            } else {
                showToast("Cannot delete post at the moment!")
            }
        })
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}
