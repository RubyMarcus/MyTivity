package com.apia22018.sportactivities.screens.message


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.data.listActivities.Activities

import com.apia22018.sportactivities.databinding.MessageFragmentBinding
import com.apia22018.sportactivities.utils.InjectorUtils

class MessageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val activities: Activities = savedInstanceState?.getParcelable(VALUE) ?: Activities()
        val binding = MessageFragmentBinding.inflate(inflater, container, false)
        val adapter = MessageAdapter()
        binding.messagesRecyclerView.adapter = adapter
        subscribeUI(adapter, activities.activityId)
        return binding.root
    }

    private fun subscribeUI(adapter: MessageAdapter, activityId: String) {
        val factory: MessageViewModelFactory = InjectorUtils.provideMessageViewModelFactory("0TbVNwt9jQhHmEg6FJI7gHTjLPb2")
        val viewModel = ViewModelProviders.of(this, factory)
                .get(MessageViewModel::class.java)

        viewModel.getMessages().observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                adapter.setMessages(it)
            }
        })
    }

    companion object {
        private const val VALUE = "value"

        fun newInstance() = MessageFragment()
    }

}
