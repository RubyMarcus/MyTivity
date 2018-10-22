package com.apia22018.sportactivities.screens.message

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.data.messages.Message

class MessageAdapter : RecyclerView.Adapter<MessageViewHolder>() {
    private val messages: MutableList<Message> = mutableListOf()

    fun setMessages(message: List<Message>) {
        this.messages.apply {
            clear()
            addAll(message)
        }
        notifyDataSetChanged()
    }

    private fun createOnClickListener(message: Message): View.OnClickListener {
        return View.OnClickListener {
            println(" SOME KIND OF BUTTON CLICKED $message")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): MessageViewHolder = MessageViewHolder.newInstance(parent)

    override fun getItemCount(): Int = this.messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = this.messages[position]
        holder.apply {
            bind(createOnClickListener(message), message)
        }
    }
}