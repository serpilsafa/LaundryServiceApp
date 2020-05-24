package com.safa.laundryserviceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.safa.laundryserviceapp.R
import com.safa.laundryserviceapp.model.CategoryDetail

class CategoryRecycleViewAdapter(val categoryList: ArrayList<CategoryDetail>): RecyclerView.Adapter<CategoryRecycleViewAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.category_receycleview_row,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }
}