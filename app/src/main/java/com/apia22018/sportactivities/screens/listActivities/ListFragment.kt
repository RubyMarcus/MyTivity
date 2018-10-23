package com.apia22018.sportactivities.screens.listActivities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.BR.viewModel
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.databinding.FragmentActivityListBinding
import com.apia22018.sportactivities.databinding.ListActivityBinding
import com.apia22018.sportactivities.utils.InjectorUtils
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_activity_list.*


class ListFragment : Fragment() {

    private lateinit var binding: FragmentActivityListBinding
    private lateinit var viewModel: ActivitiesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity_list, container, false)

        val factory: ActivitiesViewModelFactory = InjectorUtils.provideActivitiesViewModelFactory()
        viewModel = ViewModelProviders
                .of(this, factory)
                .get(ActivitiesViewModel::class.java)

        val adapter = ActivitiesAdapter()

        val list = binding.recyclerviewListActivities
        list.layoutManager = LinearLayoutManager(activity)
        binding.recyclerviewListActivities.adapter = adapter

        subscribeUI(adapter)

        return binding.root
    }

    private fun subscribeUI(adapter: ActivitiesAdapter) {
        viewModel.getActivities().observe(this, Observer { activities ->
            if (activities != null) adapter.submitList(activities)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}