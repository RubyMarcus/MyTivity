package com.apia22018.sportactivities.screens.message

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.data.messages.Message
import com.apia22018.sportactivities.databinding.MessageListItemBinding

class MessageViewHolder(private val binding: MessageListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun newInstance(parent: ViewGroup): MessageViewHolder {
            return MessageViewHolder(MessageListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
            ))
        }
    }

    fun bind(messageViewModel: MessageViewModel, item: Message) {
        binding.apply {
            viewModel = messageViewModel
            message = item
            executePendingBindings()
        }
    }
}