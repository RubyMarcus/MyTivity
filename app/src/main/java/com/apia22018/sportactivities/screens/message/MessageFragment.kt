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
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.databinding.MessageListFragmentBinding
import com.apia22018.sportactivities.utils.InjectorUtils
import com.apia22018.sportactivities.utils.hideKeyboard
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.message_list_fragment.*

class MessageFragment : Fragment() {
    private lateinit var viewModel: MessageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val bundle = arguments ?: Bundle()
        val activity: Activities = bundle.getParcelable(VALUE) ?: Activities()

        val binding = MessageListFragmentBinding.inflate(inflater, container, false)
        val factory: MessageViewModelFactory = InjectorUtils.provideMessageViewModelFactory(activity)
        viewModel = ViewModelProviders.of(this, factory)
                .get(MessageViewModel::class.java)
        binding.viewModel = viewModel

        val adapter = MessageAdapter(viewModel)
        binding.messagesRecyclerView.adapter = adapter

        subscribeUI(adapter, binding.root)
        return binding.root
    }

    private fun subscribeUI(adapter: MessageAdapter, view: View) {
        this.viewModel.getMessages().observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                adapter.setMessages(it)
                messages_recycler_view.scrollToPosition(it.size-1)
            }
        })

        this.viewModel.createMessage.observe(this, Observer {
            if (it != null && it) {
                val user = FirebaseAuth.getInstance().currentUser
                val textInput = view.findViewById<EditText>(R.id.message_text_input)
                val text = textInput.text.toString()
                textInput.text?.clear()
                textInput.clearFocus()
                view.hideKeyboard()

                user?.email?.let {
                    viewModel.postMessage(text, it)
                }
            }
        })
    }

    companion object {
        private const val VALUE = "value"

        fun newInstance(activities: Activities) = MessageFragment().apply {
            arguments = Bundle().apply {
                putParcelable(VALUE, activities)
            }
        }

    }

}
