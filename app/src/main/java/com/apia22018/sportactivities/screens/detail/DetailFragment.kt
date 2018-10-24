package com.apia22018.sportactivities.screens.detail


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.databinding.DetailFragmentBinding
import com.apia22018.sportactivities.utils.InjectorUtils

class DetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val bundle = arguments ?: Bundle()
        val activity: Activities = bundle.getParcelable(VALUE) ?: Activities()

        val binding = DetailFragmentBinding.inflate(inflater, container, false)
        val factory = InjectorUtils.provideDetailViewModelFactory(activity)
        viewModel = ViewModelProviders.of(this, factory)
                .get(DetailViewModel::class.java)

        binding.viewModel = viewModel
        binding.activity = activity
        binding.executePendingBindings()

        return binding.root
    }

    companion object {
        private const val VALUE = "value"
        fun newInstance(activities: Activities) = DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(VALUE, activities)
            }
        }
    }


}
