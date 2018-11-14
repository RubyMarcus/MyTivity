package com.apia22018.sportactivities.screens.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.databinding.DetailFragmentBinding
import com.apia22018.sportactivities.utils.InjectorUtils
import com.apia22018.sportactivities.utils.showSnackbar

class DetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val bundle = arguments ?: Bundle()
        val activities: Activities = bundle.getParcelable(DetailFragment.VALUE) ?: Activities()


        val binding = DetailFragmentBinding.inflate(inflater, container, false)
        val factory = InjectorUtils.provideDetailViewModelFactory(activities)
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
            if (it != null) {
                adapter.setAttendees(it)
                viewModel.checkIfUserCanJoinEvent(it)
            }
        })

        this.viewModel.getActivity().observe(this, Observer {
            if (it != null) {
                binding.activity = it
                viewModel.stopSpinner()
            }
        })

        this.viewModel.displaySnackBar.observe(this, Observer {
            if (it != null) {
                displaySnackbar(binding.root, it, Snackbar.LENGTH_LONG)
            }
        })

        this.viewModel.displayDialog.observe(this, Observer {
            if (it != null && it) {
                showCreateDialog("Are you sure you want to delete the event?")
            }
        })
    }

    private fun closeFragment(){
        activity?.run {
            finish()
        }
    }

    private fun displaySnackbar(view: View, text: String, length: Int) {
        view.showSnackbar(text, length)
    }

    private fun showCreateDialog(textInfo: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Remove Activity")
        val view = layoutInflater.inflate(R.layout.dialog, null)
        builder.setView(view)
        val firstNameView = view.findViewById(R.id.dialogInfo) as TextView
        firstNameView.text = textInfo
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            viewModel.deleteActivity { removed ->
                if (removed) {
                    closeFragment()
                }
            }
        }
        builder.setNegativeButton(android.R.string.cancel) { _, _ -> }
        builder.show()
    }

    companion object {
        private const val VALUE = "value"
        fun newInstance(activity: Activities) = DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(VALUE, activity)
            }
        }
    }
}
