package com.safa.laundryserviceapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.safa.laundryserviceapp.R
import com.safa.laundryserviceapp.adapter.CategoryRecycleViewAdapter
import kotlinx.android.synthetic.main.fragment_first_category.*

class FirstCategoryFragment : Fragment() {

    private val categoryAdapter = CategoryRecycleViewAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.first_category_recycleView.layoutManager = LinearLayoutManager(context)
        first_category_recycleView.adapter = categoryAdapter

    }

}
