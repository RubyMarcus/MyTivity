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
import kotlinx.android.synthetic.main.message_fragment.*

class MessageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val activities: Activities = savedInstanceState?.getParcelable(VALUE) ?: Activities()
        val binding = MessageFragmentBinding.inflate(inflater, container, false)
        val adapter = MessageAdapter()
        binding.messagesRecyclerView.adapter = adapter
        val factory: MessageViewModelFactory = InjectorUtils.provideMessageViewModelFactory("0TbVNwt9jQhHmEg6FJI7gHTjLPb2")
        val viewModel = ViewModelProviders.of(this, factory)
                .get(MessageViewModel::class.java)
        binding.viewModel = viewModel
        subscribeUI(adapter, viewModel, activities.activityId)
        return binding.root
    }

    // TODO("USE THE CORRECT ACTIVTY ID!!!!!!!!!!!!!!!!!!!!)
    private fun subscribeUI(adapter: MessageAdapter, viewModel: MessageViewModel, activityId: String) {
        viewModel.getMessages().observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                adapter.setMessages(it)
            }
        })

        // TODO("USE THE CORRECT USERNAME!!!!!!!!!!!!!!!!!!!!)
        viewModel.createMessage.observe(this, Observer {
            if (it != null && it) {
                val text = message_text_input.text.toString()
                viewModel.postMessage(text, "username")
            }
        })
    }

    companion object {
        private const val VALUE = "value"

        fun newInstance() = MessageFragment()
    }

}
