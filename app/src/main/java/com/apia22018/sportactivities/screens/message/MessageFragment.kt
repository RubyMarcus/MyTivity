package com.apia22018.sportactivities.screens.message


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.databinding.MessageFragmentBinding
import com.apia22018.sportactivities.utils.InjectorUtils

class MessageFragment : Fragment() {
    private lateinit var viewModel: MessageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val activities: Activities = savedInstanceState?.getParcelable(VALUE) ?: Activities()
        val binding = MessageFragmentBinding.inflate(inflater, container, false)
        val factory: MessageViewModelFactory = InjectorUtils.provideMessageViewModelFactory("0TbVNwt9jQhHmEg6FJI7gHTjLPb2")
        viewModel = ViewModelProviders.of(this, factory)
                .get(MessageViewModel::class.java)
        binding.viewModel = viewModel
        val adapter = MessageAdapter(viewModel)
        binding.messagesRecyclerView.adapter = adapter

        subscribeUI(adapter, binding.root, activities.activityId)
        return binding.root
    }

    // TODO("USE THE CORRECT ACTIVTY ID!!!!!!!!!!!!!!!!!!!!)
    private fun subscribeUI(adapter: MessageAdapter, view: View, activityId: String) {
        this.viewModel.getMessages().observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                adapter.setMessages(it)
            }
        })

        // TODO("USE THE CORRECT USERNAME!!!!!!!!!!!!!!!!!!!!)
        this.viewModel.createMessage.observe(this, Observer {
            if (it != null && it) {
                val text = view.findViewById<EditText>(R.id.message_text_input).text.toString()
                viewModel.postMessage(text, "username")
            }
        })
    }

    companion object {
        private const val VALUE = "value"

        fun newInstance() = MessageFragment()
    }

}
