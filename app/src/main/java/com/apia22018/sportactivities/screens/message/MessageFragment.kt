package com.apia22018.sportactivities.screens.message


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
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
import com.apia22018.sportactivities.utils.showSnackbar
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
            if (it != null) {
                adapter.setMessages(it)
                messages_recycler_view.scrollToPosition(it.size - 1)
                if (!it.isEmpty()) {
                    no_messages.visibility = View.GONE
                } else {
                    no_messages.visibility = View.VISIBLE
                }
            }
        })

        this.viewModel.displaySnackBar.observe(this, Observer {
            if (it != null) {
                view.showSnackbar(getString(it), Snackbar.LENGTH_LONG)
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
