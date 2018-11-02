package com.apia22018.sportactivities.screens.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.databinding.DetailFragmentBinding
import com.apia22018.sportactivities.utils.InjectorUtils

class DetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val bundle = arguments ?: Bundle()
        val activityId: String = bundle.getString(VALUE) ?: ""

        val binding = DetailFragmentBinding.inflate(inflater, container, false)
        val factory = InjectorUtils.provideDetailViewModelFactory(activityId)
        viewModel = ViewModelProviders.of(this, factory)
                .get(DetailViewModel::class.java)

        val adapter = DetailAttendeeAdapter(viewModel)

        binding.attendeeList.layoutManager = LinearLayoutManager(activity)
        binding.attendeeList.adapter = adapter
        binding.viewModel = viewModel
        binding.executePendingBindings()

        subscribeUI(binding, adapter)

        return binding.root
    }

    private fun subscribeUI(binding: DetailFragmentBinding, adapter: DetailAttendeeAdapter) {
        this.viewModel.getAttendees().observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                adapter.setAttendees(it)
            }
        })

        this.viewModel.getActivity().observe(this, Observer {
            if (it != null) {
                binding.activity = it
            }
        })
    }

    companion object {
        private const val VALUE = "value"
        fun newInstance(activityId: String) = DetailFragment().apply {
            arguments = Bundle().apply {
                putString(VALUE, activityId)
            }
        }
    }
}
