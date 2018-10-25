package com.apia22018.sportactivities.screens.attendee


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.databinding.AttendeeListFragmentBinding

class AttendeeFragment : Fragment() {

    private lateinit var binding: AttendeeListFragmentBinding
    //private lateinit var viewModel: ActivitiesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.attendee_list_fragment, container, false)

        /*val factory: ActivitiesViewModelFactory = InjectorUtils.provideActivitiesViewModelFactory()
        viewModel = ViewModelProviders
                .of(this, factory)
                .get(ActivitiesViewModel::class.java)*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val adapter = ActivitiesAdapter()

        val list = binding.attendeeList
        list.layoutManager = LinearLayoutManager(activity)
        //list.adapter = adapter

        //subscribeUI(adapter)
    }


}
