package com.apia22018.sportactivities.screens.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.databinding.ActivitiesListFragmentBinding
import com.apia22018.sportactivities.utils.InjectorUtils

class ListFragment : Fragment() {
    private lateinit var viewModel: ActivitiesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ActivitiesListFragmentBinding = ActivitiesListFragmentBinding.inflate(inflater, container, false)
        val factory: ActivitiesViewModelFactory = InjectorUtils.provideActivitiesViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(ActivitiesViewModel::class.java)

        val adapter = ActivitiesAdapter()

        binding.recyclerviewListActivities.layoutManager = LinearLayoutManager(activity)
        binding.recyclerviewListActivities.adapter = adapter
        binding.viewModel = viewModel
        binding.isLoading = true

        subscribeUI(adapter, binding)

        return binding.root
    }

    private fun subscribeUI(adapter: ActivitiesAdapter, binding: ActivitiesListFragmentBinding) {
        viewModel.getActivities().observe(this, Observer { activities ->
            if (activities != null) {
                binding.isLoading = false
                adapter.submitList(activities)
            } else {
                binding.isLoading = true
            }
            binding.executePendingBindings()
        })
    }

    companion object {
        fun newInstance() = ListFragment()
    }
}