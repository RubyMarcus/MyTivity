package com.apia22018.sportactivities.screens.detailActivity


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.data.listActivities.Activities

class DetailActivityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail_activity_fragment, container, false)
    }

    companion object {
        private const val VALUE = "value"
        fun newInstance(activities: Activities) = DetailActivityFragment().apply {
            arguments = Bundle().apply {
                putParcelable(VALUE, activities)
            }
        }
    }


}
