package com.apia22018.sportactivities.screens.detail

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.databinding.DetailFragmentBinding
import com.apia22018.sportactivities.screens.alertDialog.alertFragment
import com.apia22018.sportactivities.utils.InjectorUtils
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: DetailFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val bundle = arguments ?: Bundle()
        val activityId: String = bundle.getString(VALUE) ?: ""

        binding = DetailFragmentBinding.inflate(inflater, container, false)
        val factory = InjectorUtils.provideDetailViewModelFactory(activityId)
        viewModel = ViewModelProviders.of(this, factory)
                .get(DetailViewModel::class.java)

        binding.viewModel = viewModel
        binding.executePendingBindings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DetailAttendeeAdapter(viewModel)

        val list = binding.attendeeList
        list.layoutManager = LinearLayoutManager(activity)
        list.adapter = adapter

        subscribeUI(adapter)

        join_event.setOnClickListener{

            var detailAlert = alertFragment()
            detailAlert.message = R.string.detail_message
            if (detailAlert.confirmation) {

            }
            else
            {

            }


            /*val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton("Yes"
                    ) { dialog, id ->

                    }
                    setNegativeButton("Cancel"
                    ) { dialog, id ->
                        print("Cancelled")
                    }
                }

                builder.setMessage("Are you sure you want to join this activity?")

                // Create the AlertDialog
                builder.show()
            }
            */
        }


    }

    private fun subscribeUI(adapter: DetailAttendeeAdapter) {
        this.viewModel.getAttendees().observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                adapter.setAttendees(it)
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
