package com.example.restapiidemo.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.FoodModel
import kotlinx.android.synthetic.main.home_rv_item_view.view.*

class HomeAdapter(var listener: HomeListener) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var data: ArrayList<FoodModel>? = null

    interface HomeListener {
        fun onItemDeleted(foodModel: FoodModel, position: Int)
    }

    fun setData(list: ArrayList<FoodModel>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_rv_item_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item)
        holder.itemView.img_delete.setOnClickListener {
            item?.let { it1 ->
                listener.onItemDeleted(it1, position)
            }
        }
    }

    fun addData(foodModel: FoodModel) {
        data?.add(0, foodModel)
        notifyItemInserted(0)
    }

    fun removeData(position: Int) {
        data?.removeAt(position)
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: FoodModel?) {
            itemView.tv_home_item_title.text = item?.name
        }
    }
}