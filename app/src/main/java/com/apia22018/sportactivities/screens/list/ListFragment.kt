package com.apia22018.sportactivities.screens.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.databinding.ActivitiesListFragmentBinding
import com.apia22018.sportactivities.utils.InjectorUtils

class ListFragment : Fragment() {

    private lateinit var binding: ActivitiesListFragmentBinding
    private lateinit var viewModel: ActivitiesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activities_list_fragment, container, false)

        val factory: ActivitiesViewModelFactory = InjectorUtils.provideActivitiesViewModelFactory()
        viewModel = ViewModelProviders
                .of(this, factory)
                .get(ActivitiesViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ActivitiesAdapter()

        val list = binding.recyclerviewListActivities
        list.layoutManager = LinearLayoutManager(activity)
        binding.recyclerviewListActivities.adapter = adapter

        subscribeUI(adapter)
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